package monitorsv2;

public class Thread_2_DR extends Thread
{
    private appInterface app;
    private byte[] mensagem;
    
    public Thread_2_DR(appInterface app, byte[] mensagem) 
    {
        this.app = app;
        this.mensagem = mensagem;
        start();
    }
    
    public void run()
    {
           // remover byte de sincronizaçãp
           byte[] mensagem_sem = Utils.removeSincronizacao(mensagem);

           // remover 255
           byte[] mensagem_sem_e = Utils.removeEscape(mensagem_sem);
           
             // trocar a ordem
           byte[] mensagem_limpa = Utils.trocaOrdem(mensagem_sem_e);
           // interpretar 
                      
           String trans_len = "<Trans_len = "+ Utils.converteInt(mensagem_limpa[0],mensagem_limpa[1])+"> ";
           String dest_id = "<dest_id = " + Utils.converteInt(mensagem_limpa[2],mensagem_limpa[3])+"> ";
           String src_id = "<src_id = " + Utils.converteInt(mensagem_limpa[4],mensagem_limpa[5]) + "> ";
           String cmd = "<cmd = " + Utils.converteInt(mensagem_limpa[6],mensagem_limpa[7]) + "> ";
           String rsp = "<rsp = " + Utils.converteInt(mensagem_limpa[8],mensagem_limpa[9]) + "> ";
           
        
           //compila mensagem toda
           String resposta = "Mensagem Disconnect Response recebida: \n" + trans_len + dest_id + src_id + cmd + rsp + "\n \n";
                      
           //imprimir no appInterface
           app.appendText(resposta);
                              
    }    
}
