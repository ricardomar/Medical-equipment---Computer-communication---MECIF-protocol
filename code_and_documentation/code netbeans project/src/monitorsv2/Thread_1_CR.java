
package monitorsv2;



public class Thread_1_CR extends Thread 
{
    private ComInterface port;
    private appInterface app;
    private byte[] buffer;   
    private int pos_inicio_escrita;
    
    public Thread_1_CR(ComInterface port, appInterface app) 
    {
        this.port = port;
        this.app = app;
        this.buffer = new byte[2*1024];
        this.pos_inicio_escrita = 0;
        start();
    }
    
    public void run()
    {   

        while(true)
        {
            byte[] aux = port.readBytes();
            
            if(aux.length ==0)
            {                
                byte[] mensagem = Utils.extraiMensagem(buffer,0 ,pos_inicio_escrita -1);
                
                //mensagem completa... enviá-la para rotina de processamento de mensagens dedicada a este tipo de mensagens
                Thread_2_CR t2 = new Thread_2_CR(app, mensagem);
                
                break;
            }
            else
            {
                //mete no buffer os bytes tal como chegam
                Utils.meteBuffer(buffer,aux,pos_inicio_escrita);
                //actualiza posição de escrita
                pos_inicio_escrita = pos_inicio_escrita + aux.length;
            }
            
            try
            {
                 Thread.sleep(100);
            }
            catch (Exception e)
            {
                  System.out.println(e);
            }
        }    
    }    
}
