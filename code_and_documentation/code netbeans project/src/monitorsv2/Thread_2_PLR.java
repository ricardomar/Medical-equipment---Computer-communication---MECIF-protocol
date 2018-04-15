package monitorsv2;

public class Thread_2_PLR  extends Thread
{   
    private appInterface app;
    private byte[] mensagem;
    
    public Thread_2_PLR(appInterface app, byte[] mensagem) 
    {
        this.app = app;
        this.mensagem = mensagem;
        start();
    }
    
    public void run()
    {
        
           String trans_len = "<Trans_len = "+ Utils.converteInt(mensagem[0],mensagem[1])+"> ";
           String dest_id = "<dest_id = " + Utils.converteInt(mensagem[2],mensagem[3])+"> ";
           String src_id = "<src_id = " + Utils.converteInt(mensagem[4],mensagem[5]) + "> ";
           String cmd = "<cmd = " + Utils.converteInt(mensagem[6],mensagem[7]) + "> ";         
           String actual = "<actual = " + Utils.converteInt((byte) 0, mensagem[8]) + "> ";
           String total = "<total = " + Utils.converteInt((byte) 0, mensagem[9]) + "> ";           
                      
           String resposta_parte_1 = "\n" + trans_len + dest_id + src_id + cmd + actual + total ;
           
           int nListInfo = (mensagem.length - 10) / 26;
           int posicao = 10;
           
           
           String resposta_parte_2 = "";
           
           for (int i = 0; i < nListInfo; i++)
           {
                String SourceId = "<SourceId = "+ Utils.converteInt(mensagem[posicao],mensagem[posicao + 1]) +  ">";
                String ChannelId = "<ChannelId = "+ Utils.converteInt(mensagem[posicao + 2],mensagem[posicao + 3]) +  ">";
                String msgType = "<msgType = "+ Utils.converteInt(mensagem[posicao + 4],mensagem[posicao + 5]) +  ">";
                String SourceNo = "<SourceNo = " + Utils.converteInt((byte) 0, mensagem[posicao + 6]) +">";
                String ChannelNo = "<ChannelNo = " + Utils.converteInt((byte) 0, mensagem[posicao + 7]) +">";
                
                String Layer = "<Layer = " + Utils.converteInt((byte) 0, mensagem[posicao + 8]) +">";
                String Unused = "<Unused = " + Utils.converteInt((byte) 0, mensagem[posicao + 9]) +">";
                                
                int[] ascii_id = new int[8];
                ascii_id[0] = Utils.converteInt(mensagem[posicao + 10], mensagem[posicao +11]);
                ascii_id[1] = Utils.converteInt(mensagem[posicao + 12], mensagem[posicao +13]);
                ascii_id[2] = Utils.converteInt(mensagem[posicao + 14], mensagem[posicao +15]);
                ascii_id[3] = Utils.converteInt(mensagem[posicao + 16], mensagem[posicao +17]);
                ascii_id[4] = Utils.converteInt(mensagem[posicao + 18], mensagem[posicao +19]);
                ascii_id[5] = Utils.converteInt(mensagem[posicao + 20], mensagem[posicao +21]);
                ascii_id[6] = Utils.converteInt(mensagem[posicao + 22], mensagem[posicao +23]);
                ascii_id[7] = Utils.converteInt(mensagem[posicao + 24], mensagem[posicao +25]);
                String Ascii_ID = "<Ascii_ID = " + AsciiConversions.c16_to_c8(ascii_id) +">";
                
                posicao = posicao + 26;
                
                resposta_parte_2 = resposta_parte_2 + " " + SourceId + " " + ChannelId + " " + msgType + " " + SourceNo + " " + ChannelNo + " " + Layer +  " " + Unused +  " " + Ascii_ID;
           }
           
           app.appendText(resposta_parte_1 + resposta_parte_2);
           
    
    }        
}
