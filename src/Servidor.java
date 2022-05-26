
/* Pacotes usados para montar a classe servidor. Nada mais � do que c�digos
   j� montados que utilizamos para diminuir o c�digo, assim poupando de fazer 
   toda uma classe nova dentro desta nossa.(Cada pacote ser� explicado 
   durante o c�digo para que a linha de racioc�nio seja melhor)*/
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;//classe que nos possibilita ler na forma de input esse Stream em byte, um byte por vez.
import java.io.InputStreamReader; //classe que nos possibilita apenas a leitura em byte, um byte por vez.
import java.io.OutputStream;//classe de saida dos dados Stream em byte, um byte por vez.
import java.io.OutputStreamWriter; //classe que nos possibilita a escrita no formato Stream em byte, um byte por vez.
import java.io.Writer;  //classe de escrica de (char[], int, int).
import java.net.ServerSocket; //classe de conex�o a um servidor
import java.net.Socket;  //Ponto de cominica��o entre duas m�quinas
import java.util.ArrayList; // classe para implementar um array list
import javax.swing.JLabel;  //classe de apresenta��o de label
import javax.swing.JOptionPane;// painel de dialogo com usuario para apresenta��o ou recebimento de informa��o
import javax.swing.JTextField; // classe que permite entrada de texto



/* A classe Servidor,java j� � cetada para ser uma thread, assim adotando todos
   os comportamentos e propriedades da classe que � executar tarefas paralelas
   simultaneamente.*/
public class Servidor extends Thread {


    // Aqui temos as declara��es dos atributos est�ticos e de inst�ncia:
/* Cliente por exemplo � para armazenar a mensagem de cada cliente, por isso 
   o Arraylist.*/
    private static ArrayList<BufferedWriter> clientes;
    // J� o serveSocket � para criar o servidor, um apenas neste caso.
    private static ServerSocket server;
    // Objeto String que o m�todo ir� receber.
    private String nome;
    // Objeto Socket que o m�todo ir� receber.
    private Socket con;
    // Objeto InputStream que o m�todo ir� receber.
    private InputStream in;
    // Objeto InputStreamReader que o m�todo ir� receber.
    private InputStreamReader inr;
    // Objeto BufferedReader que o m�todo ir� receber.
    private BufferedReader bfr;

    /*Aqui temos o m�todo construtor que recebe o objeto socket como par�metro
      que cria outro objeto tido BufferedReader, que aponta para stream do 
      cliente.*/
    public Servidor(Socket con) {
        this.con = con;
        /*c�digo que inclui comandos/invoca��es de m�todos que podem gerar uma 
      situa��o de exce��o.*/
        try {
            /* getInputStream nos retorna exatamente o que o cliente est� enviando
       ?do outro lado?. O conceito de Stream � um bloco gen�rico 
       para algum tipo de dados, podendo ele ser texto, v�deo, imagem e etc, 
       realmente n�o importa.*/
            in = con.getInputStream();
            /* A fun��o do InputStreamReader � servir como um adaptador entre as duas 
       classes - l� bytes de um lado, converte em caracteres do outro, atrav�s 
       do uso de uma codifica��o de caracteres. Ou seja, ele � um Reader que 
       recebe um InputStream na constru��o, consumindo dados desse stream e 
      apresentando-os como caracteres para o consumidor.*/
            inr = new InputStreamReader(in);
            /* BufferedReader : L� os caracteres um a um agregando-os em um buffer at� 
      encontrar o car�ter de fim de linha. Ent�o esse conjunto de caracteres 
      pode ser tranforamdo em uma string e retornada como resultado desee 
      m�todo).*/
            bfr = new BufferedReader(inr);
            /* IOException, que indica a ocorr�ncia de algum tipo de erro em opera��es 
          de entrada e sa�da.*/
        } catch (IOException e) {
            /* printStackTrace m�todo em Java � uma ferramenta usada para manusear 
       exce��es e erros.*/
            e.printStackTrace();
        }
    }

    /*O met�do "run" � acionado e alocado em uma Thread e tamb�m verifica se existe
      uma nova mensagem do cliente que conectou, e caso exista, ele levar� para
      o proximo met�do.*/
    public void run() {

        try {

            String msg;
            /*OutputStream � capaz de enviar dados a um determinado Stream, 
              ao contr�rio do InputStream que faz a leitura do mesmo, e j� usamos
              getOutputStream() que nos retorna uma inst�ncia de OutputStream onde 
              podemos escrever o que quisermos e o servidor ir� receber atrav�s do 
              InputStream.*/
            OutputStream ou = this.con.getOutputStream();
            /*Cria-se uma instancia writer para gravar uma string especifica no 
              fluxo e a classe OutputStreamWriter serve para converter caracteres
              em bytes.*/
            Writer ouw = new OutputStreamWriter(ou);
            /*O BufferedWriter envia os dados para a sa�da desejada imediatamente
              ao inv�s de ir um por vez.*/
            BufferedWriter bfw = new BufferedWriter(ouw);
            clientes.add(bfw);
            nome = msg = bfr.readLine();

            while (!"Sair".equalsIgnoreCase(msg) && msg != null) {
                msg = bfr.readLine(); // leitura de cada linha
                sendToAll(bfw, msg); //metodo de envio de mensagem para todos os clientes.
                System.out.println(msg);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    /*Met�do que faz a mensagem enviada pelo cliente1 ser mandada para o servidor
      onde os outros clientes leem a copia.*/
    public void sendToAll(BufferedWriter bwSaida, String msg) throws IOException {
        BufferedWriter bwS;

        /* la�o que percorre as informa��es dos clientes */
        for (BufferedWriter bw : clientes) {
            bwS = (BufferedWriter) bw;
            //verifica��o se cliente n�o pressionou o bot�o sair
            if (!(bwSaida == bwS)) {
                // apresenta��o do nome do cliente concatenado com a mensagem digitada
                bw.write(nome + " -> " + msg + "\r\n");
                bw.flush(); //limpa o buffer da memoria
            }
        }
    }

    /*Met�do principal que iniciara o servidor. Far� a configura��o do servidor 
      socket e sua respectiva porta*/
    public static void main(String[] args) {

        try {
            //Cria os objetos necess�rio para inst�nciar o servidor
            
            /*JLabels s�o r�tulos que podemos exibir em nossos frames.
              S�o elementos est�ticos, n�o sendo usado para interagir com o 
              usu�rio*/
            JLabel lblMessage = new JLabel("Porta do Servidor:");
            /*representa um campo de texto onde o usu�rio pode informar um texto 
              em uma linha, linha essa que ser� a senha para entrar na porta*/
            JTextField txtPorta = new JTextField("12345");
            /**/
            Object[] texts = {lblMessage, txtPorta};
            /*JOptionPane possibilita a cria��o de uma caixa de dialogo padr�o 
            que ou solicita um valor para o usu�rio ou retorna uma informa��o.*/
            JOptionPane.showMessageDialog(null, texts);
            server = new ServerSocket(Integer.parseInt(txtPorta.getText())); //captura o texto da porta e converte para integer e passa por parametro par a classe ServerSocket
            clientes = new ArrayList<BufferedWriter>(); // criando um arraylist do tipo BufferedWriter
            JOptionPane.showMessageDialog(null, "Servidor ativo na porta: "
                    + txtPorta.getText());

            /*Condicional de quando for verdadeira ele ir� startar o servidor
              e escrevera as mensagens de aguardo, e quando o cliente conectar
              ira aparecer uma mensagem informando a conex�o*/
            while (true) {
                System.out.println("Aguardando conex�o...");
                Socket con = server.accept(); // se comunica��o n�o for close ou n�o vinculada retorna conex�o valida
                System.out.println("Cliente conectado...");
                Thread t = new Servidor(con); // criando uma thread para permitir a libera��o de comunica��o individual do cliente e servidor
                t.start(); // metodo para estartar a comunica��o cliente/servidor
            }

        } catch (Exception e) {

            e.printStackTrace();//tra�a o catch para apresentar o erro caso ocorra
        }
    }// Fim do m�todo main
} //Fim da classe

