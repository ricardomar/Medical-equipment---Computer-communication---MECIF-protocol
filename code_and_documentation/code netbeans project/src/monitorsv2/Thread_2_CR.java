
package monitorsv2;

public class Thread_2_CR extends Thread
{
    private appInterface app;
    private byte[] mensagem;
    
    public Thread_2_CR(appInterface app, byte[] mensagem) 
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
           String windows = "<window = " + Utils.converteInt(mensagem_limpa[8],mensagem_limpa[9]) + "> ";
           
           //ver se e mesmo esta ordem
           String compact_high = "<compact_high = " + Utils.converteInt((byte) 0,mensagem_limpa[10]) + "> ";
           String compact_low = "<compact_low = " + Utils.converteInt((byte) 0,mensagem_limpa[11]) + "> ";
           
           
           //ver se é esta ordem
           String error = "<error = " + Utils.converteInt((byte) 0,mensagem_limpa[12]) + "> ";
           String ret = "<return = " + Utils.converteInt((byte) 0,mensagem_limpa[13]) + "> ";
           
           
           //compila mensagem toda
           String resposta = "Mensagem Connect Response recebida: \n" + trans_len + dest_id + src_id + cmd + windows + compact_high + compact_low + ret + error + "\n \n";
                      
           //inprimir no appInterface
           app.appendText(resposta);
                                        
    }    
}
