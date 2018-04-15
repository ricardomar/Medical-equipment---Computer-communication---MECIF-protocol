package monitorsv2;

public class CMSInterface {

    public static void connect(ComInterface port,appInterface app ) 
    {

        
        byte[] mensagem = Utils.constroiCR(32865,10,0);
        port.writeBytes(mensagem);
        app.appendText("Mensagem Connect Request enviada... \n \n");
        
        try
        {
        while(port.bytesAvailable()!=0)
            Thread.sleep(100);
        
        Thread.sleep(1000);
        }
        catch(Exception e)
        {
              
        }
      
        byte[] resposta = port.readBytes();
                
        Thread_2_CR t2 = new Thread_2_CR(app,resposta);       
    }

    public static void disconnect(ComInterface port,appInterface app)    
    {        
        byte[] mensagem = Utils.constroiDR(32865,10);
                
        port.writeBytes(mensagem);        
        app.appendText("Mensagem Disconnect Request enviada... \n \n");
        
        try
        {
               while(port.bytesAvailable()!=0)
               Thread.sleep(100);
        
              Thread.sleep(1000);
        }
        catch(Exception e)
        {
              
        }
        
        byte[] resposta = port.readBytes();
        
        
        
        Thread_2_DR t2 = new Thread_2_DR(app,resposta);
      
        

    }

    public static void getParList(ComInterface port,appInterface app) 
    {              
        byte[] mensagem = Utils.constroiPLR(32865,10);
        port.writeBytes(mensagem);
     
        app.appendText("Mensagem Par_List Request enviada... \n \n");
        
        Thread_1_PLR t1 = new Thread_1_PLR(port, app);                      
    
    }

    public static void singleTuneRequest(int id)
    {

    }

}