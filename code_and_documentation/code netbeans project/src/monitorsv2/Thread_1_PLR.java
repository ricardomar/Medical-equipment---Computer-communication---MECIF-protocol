
package monitorsv2;

public class Thread_1_PLR  extends Thread
{
    
    ComInterface port;
    appInterface app;
    byte[] buffer;
    int inicio_msg;
    int inicio_escrita;
    
    public Thread_1_PLR(ComInterface port, appInterface app)
    {
        this.port = port;
        this.app = app;
        this.buffer = new byte[4*1024];
        this.inicio_msg = 0;
        this.inicio_escrita = 0;
        start();
    }    
    
    public void run()
    {
        boolean controlo = true;
        while(controlo)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            
            if(port.bytesAvailable() != 0)
            {
                
               
                byte[] temp = port.readBytes();
                Utils.meteBuffer(buffer,temp,inicio_escrita);
                inicio_escrita = inicio_escrita + temp.length;
                
                for(int i =(inicio_msg + 1); i < (inicio_escrita-1); i++)
                {
                    if(buffer[i]==(byte)27 && buffer[i+1] != (byte) 255)
                    {
                        byte[] mensagem_1 = Utils.extraiMensagem(buffer, inicio_msg, i-1);
                        byte[] mensagem_2 = Utils.removeSincronizacao(mensagem_1);
                        byte[] mensagem_3 = Utils.removeEscape(mensagem_2);
                        byte[] mensagem_limpa = Utils.trocaOrdem(mensagem_3);
                        
                        Thread_2_PLR t2 = new Thread_2_PLR(app, mensagem_limpa);
                        inicio_msg = i;
                    }                
                }           
            }
            
            else
            {
                byte[] mensagem_1 = Utils.extraiMensagem(buffer, inicio_msg, inicio_escrita - 1);
                byte[] mensagem_2 = Utils.removeSincronizacao(mensagem_1);
                byte[] mensagem_3 = Utils.removeEscape(mensagem_2);
                byte[] mensagem_limpa = Utils.trocaOrdem(mensagem_3);
                Thread_2_PLR t2 = new Thread_2_PLR(app, mensagem_limpa);
                break;
            }
        }    
    }
}
