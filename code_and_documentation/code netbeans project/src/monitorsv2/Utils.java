package monitorsv2;


import javax.comm.CommPortIdentifier;
import java.util.Enumeration;

public class Utils {
	static final int TYP_SPI_WS = 3;
	static final int TYP_SPI_CW = 2;
	static final int TYP_SPI_NU = 7;
	static final int SPI_GAIN_OFFSET = 15;
	static final int SPI_CALIBR_PARAM = 11;
	static final int SPI_RT_UNIT = 35;
	static final int SPI_NRM_UNIT = 47;
	static final int SPI_UNIT = 28;
	static final int SPI_NUMERIC = 17;
	static final int SPI_ALARM_LIMITS = 3;
	static final int SPI_RANGE = 24;
	static final int SPI_NUMERIC_STRING = 18;
	static final int SPI_ABS_TIME_STAMP = 1;

	static int destino = 32865;
	static int origem = 10;

	/**
	 * Lists all available communication ports (COM and LPT) on this machine
	 * @return Enumeration with all the ports
	 */

	public static Enumeration getPorts() {
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		return portList;
	}
        
        public static void meteBuffer(byte[] buffer, byte[] copiar, int pos_inicio)
        {
            for(int i = 0; i < copiar.length; i++)
            {
                   buffer[i + pos_inicio] = copiar[i];
            }        
        }
        
        public static byte[] extraiMensagem(byte[] buffer, int pos_inicio, int pos_final)
        {
            byte[] mensagem = new byte[(pos_final - pos_inicio)+1];
            
            for(int i = 0; i < mensagem.length; i++)
            {
                   mensagem[i] = buffer[pos_inicio + i];
            }
            
            return mensagem;
        }
        
        public static byte[] removeSincronizacao(byte[] mensagem)
        {
            byte[] mensagem_sem = new byte[mensagem.length-1];
            
            for(int i = 0; i <mensagem_sem.length; i++)
            {
                mensagem_sem[i]= mensagem[i+1];
            }
            
            return mensagem_sem;
        }
        
        public static byte[] trocaOrdem(byte[] mensagem)
        {
            byte[] mensagem_trocada = new byte[mensagem.length];
            
            if (mensagem.length % 2 ==0)
            {
                for(int i = 0; i < mensagem.length; i = i + 2)
                {
                    mensagem_trocada[i] = mensagem[i+1];
                    mensagem_trocada[i+1] = mensagem[i];
                }                                
            }
            else
            {
                for(int i = 0; i < (mensagem.length - 1); i= i + 2)
                {
                    mensagem_trocada[i] = mensagem[i+1];
                    mensagem_trocada[i+1] = mensagem[i];                
                }

		
		mensagem_trocada[mensagem.length-1] = mensagem[mensagem.length - 1];
            }
            
            return mensagem_trocada;
        }
        

      public static byte[] removeEscape(byte[] msg)
      {
          int contador =0;
          
          for(int i =0;i<msg.length;i++)
          {
              if(msg[i]==(byte)27)
              {
                   contador++;
              }
          }
          
          int k = 0;
          byte[] msg_e = new byte[msg.length - contador];
          
          for(int j=0;j<msg.length;j++)
          {
              if(msg[j]== (byte)27)                  
              {
                  msg_e[k]=msg[j];                 
                  j++;
              }
              else
              {
                  msg_e[k]=msg[j];
              }
              k++;
          }                    
          return msg_e;
      
        }
        
      public static int converteInt(byte byte1, byte byte0) 
      {
        //     byte 1:[byte mais significativo]        byte0: [byte menos significativo]
	return (byte0 & 0xFF) | ((byte1 & 0xFF) << 8);
      }
      
    public static byte[] converteByteArray(int inteiro)
    {
        byte[] resultado= new byte[2];
        
        //byte menos significativo
        resultado[1]=(byte)(inteiro & 0xFF);
        
        //byte mais significativo
        resultado[0]=(byte)((inteiro >> 8) & 0xFF);
        
        return resultado;
    }
    
    public static byte[] constroiCR(int dest_id, int src_id, int tick_period)
    {
        byte[] destId = converteByteArray(dest_id);
        byte[] srcId = converteByteArray(src_id);
        byte[] tickPeriod = converteByteArray(tick_period);
        
        
        
        byte[] temp1 = new byte[10];
        
        temp1[0] = (byte) 0;
        temp1[1] = (byte) 10;
        temp1[2] = destId[0];
        temp1[3] = destId[1];
        temp1[4] = srcId[0];
        temp1[5] = srcId[1];
        temp1[6] = (byte) 0;
        temp1[7] = (byte) 1;
        temp1[8] = tickPeriod[0];
        temp1[9] = tickPeriod[1];
        
        byte[] temp2 = trocaOrdem(temp1);
        
        byte[] temp3 = acrescentaEscape(temp2);
        
        byte[] mensagem = acrescentaSincronizacao(temp3);
        
        return mensagem;
    }
    
     public static byte[] constroiDR(int dest_id, int src_id)
    {
        byte[] destId = converteByteArray(dest_id);
        byte[] srcId = converteByteArray(src_id);
        
        
        
        
        byte[] temp1 = new byte[8];
        
        temp1[0] = (byte) 0;
        temp1[1] = (byte) 8;
        temp1[2] = destId[0];
        temp1[3] = destId[1];
        temp1[4] = srcId[0];
        temp1[5] = srcId[1];
        temp1[6] = (byte) 0;
        temp1[7] = (byte) 7;

        
        byte[] temp2 = trocaOrdem(temp1);
        
        byte[] temp3 = acrescentaEscape(temp2);
        
        byte[] mensagem = acrescentaSincronizacao(temp3);
        
        return mensagem;
    }
     
     public static byte[] constroiPLR(int dest_id, int src_id)
    {
        byte[] destId = converteByteArray(dest_id);
        byte[] srcId = converteByteArray(src_id);
                                
        byte[] temp1 = new byte[8];
        
        temp1[0] = (byte) 0;
        temp1[1] = (byte) 8;
        temp1[2] = destId[0];
        temp1[3] = destId[1];
        temp1[4] = srcId[0];
        temp1[5] = srcId[1];
        temp1[6] = (byte) 0;
        temp1[7] = (byte) 11;

        
        byte[] temp2 = trocaOrdem(temp1);
        
        byte[] temp3 = acrescentaEscape(temp2);
        
        byte[] mensagem = acrescentaSincronizacao(temp3);
        
        return mensagem;
    }
     
     
    
    
    public static byte[] acrescentaEscape(byte[] msg)
    {
          int contador = 0;
          
          for(int i =0;i<msg.length;i++)
          {
              if(msg[i]==(byte)27)
              {
                   contador++;
              }
          }
                    
          byte[] msg_e = new byte[msg.length+contador];
          int k = 0;
          
          for(int j=0;j<msg.length+contador;j++)
          {
              if(msg[k]== (byte)27)                  
              {
                  msg_e[j]=msg[k];
                  msg_e[j+1]=(byte)0xff;
                  j++;
              }
              else
              {
                  msg_e[j]=msg[k];
              }
              k++;
          }          
          return msg_e;
      }
    
    
    
    
    public static byte[] acrescentaSincronizacao(byte[] mensagem)
    {
        byte[] mensagem_c_e = new byte[mensagem.length +1];
        mensagem_c_e[0] = (byte) 27;
       
        for(int i = 1; i < mensagem_c_e.length; i ++ )
        {
            mensagem_c_e[i] = mensagem[i-1];        
        }
    
        return mensagem_c_e;
    }
      
     
}
        
    