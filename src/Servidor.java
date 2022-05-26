
/* Pacotes usados para montar a classe servidor. Nada mais é do que códigos
   já montados que utilizamos para diminuir o código, assim poupando de fazer 
   toda uma classe nova dentro desta nossa.(Cada pacote será explicado 
   durante o código para que a linha de raciocínio seja melhor)*/
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;//classe que nos possibilita ler na forma de input esse Stream em byte, um byte por vez.
import java.io.InputStreamReader; //classe que nos possibilita apenas a leitura em byte, um byte por vez.
import java.io.OutputStream;//classe de saida dos dados Stream em byte, um byte por vez.
import java.io.OutputStreamWriter; //classe que nos possibilita a escrita no formato Stream em byte, um byte por vez.
import java.io.Writer;  //classe de escrica de (char[], int, int).
import java.net.ServerSocket; //classe de conexão a um servidor
import java.net.Socket;  //Ponto de cominicação entre duas máquinas
import java.util.ArrayList; // classe para implementar um array list
import javax.swing.JLabel;  //classe de apresentação de label
import javax.swing.JOptionPane;// painel de dialogo com usuario para apresentação ou recebimento de informação
import javax.swing.JTextField; // classe que permite entrada de texto



/* A classe Servidor,java já é cetada para ser uma thread, assim adotando todos
   os comportamentos e propriedades da classe que é executar tarefas paralelas
   simultaneamente.*/
public class Servidor extends Thread {


    // Aqui temos as declarações dos atributos estáticos e de instância:
/* Cliente por exemplo é para armazenar a mensagem de cada cliente, por isso 
   o Arraylist.*/
    private static ArrayList<BufferedWriter> clientes;
    // Já o serveSocket é para criar o servidor, um apenas neste caso.
    private static ServerSocket server;
    // Objeto String que o método irá receber.
    private String nome;
    // Objeto Socket que o método irá receber.
    private Socket con;
    // Objeto InputStream que o método irá receber.
    private InputStream in;
    // Objeto InputStreamReader que o método irá receber.
    private InputStreamReader inr;
    // Objeto BufferedReader que o método irá receber.
    private BufferedReader bfr;

    /*Aqui temos o método construtor que recebe o objeto socket como parâmetro
      que cria outro objeto tido BufferedReader, que aponta para stream do 
      cliente.*/
    public Servidor(Socket con) {
        this.con = con;
        /*código que inclui comandos/invocações de métodos que podem gerar uma 
      situação de exceção.*/
        try {
            /* getInputStream nos retorna exatamente o que o cliente está enviando
       ?do outro lado?. O conceito de Stream é um bloco genérico 
       para algum tipo de dados, podendo ele ser texto, vídeo, imagem e etc, 
       realmente não importa.*/
            in = con.getInputStream();
            /* A função do InputStreamReader é servir como um adaptador entre as duas 
       classes - lê bytes de um lado, converte em caracteres do outro, através 
       do uso de uma codificação de caracteres. Ou seja, ele é um Reader que 
       recebe um InputStream na construção, consumindo dados desse stream e 
      apresentando-os como caracteres para o consumidor.*/
            inr = new InputStreamReader(in);
            /* BufferedReader : Lê os caracteres um a um agregando-os em um buffer até 
      encontrar o caráter de fim de linha. Então esse conjunto de caracteres 
      pode ser tranforamdo em uma string e retornada como resultado desee 
      método).*/
            bfr = new BufferedReader(inr);
            /* IOException, que indica a ocorrência de algum tipo de erro em operações 
          de entrada e saída.*/
        } catch (IOException e) {
            /* printStackTrace método em Java é uma ferramenta usada para manusear 
       exceções e erros.*/
            e.printStackTrace();
        }
    }

    /*O metódo "run" é acionado e alocado em uma Thread e também verifica se existe
      uma nova mensagem do cliente que conectou, e caso exista, ele levará para
      o proximo metódo.*/
    public void run() {

        try {

            String msg;
            /*OutputStream é capaz de enviar dados a um determinado Stream, 
              ao contrário do InputStream que faz a leitura do mesmo, e já usamos
              getOutputStream() que nos retorna uma instância de OutputStream onde 
              podemos escrever o que quisermos e o servidor irá receber através do 
              InputStream.*/
            OutputStream ou = this.con.getOutputStream();
            /*Cria-se uma instancia writer para gravar uma string especifica no 
              fluxo e a classe OutputStreamWriter serve para converter caracteres
              em bytes.*/
            Writer ouw = new OutputStreamWriter(ou);
            /*O BufferedWriter envia os dados para a saída desejada imediatamente
              ao invês de ir um por vez.*/
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
    /*Metódo que faz a mensagem enviada pelo cliente1 ser mandada para o servidor
      onde os outros clientes leem a copia.*/
    public void sendToAll(BufferedWriter bwSaida, String msg) throws IOException {
        BufferedWriter bwS;

        /* laço que percorre as informações dos clientes */
        for (BufferedWriter bw : clientes) {
            bwS = (BufferedWriter) bw;
            //verificação se cliente não pressionou o botão sair
            if (!(bwSaida == bwS)) {
                // apresentação do nome do cliente concatenado com a mensagem digitada
                bw.write(nome + " -> " + msg + "\r\n");
                bw.flush(); //limpa o buffer da memoria
            }
        }
    }

    /*Metódo principal que iniciara o servidor. Fará a configuração do servidor 
      socket e sua respectiva porta*/
    public static void main(String[] args) {

        try {
            //Cria os objetos necessário para instânciar o servidor
            
            /*JLabels são rótulos que podemos exibir em nossos frames.
              São elementos estáticos, não sendo usado para interagir com o 
              usuário*/
            JLabel lblMessage = new JLabel("Porta do Servidor:");
            /*representa um campo de texto onde o usuário pode informar um texto 
              em uma linha, linha essa que será a senha para entrar na porta*/
            JTextField txtPorta = new JTextField("12345");
            /**/
            Object[] texts = {lblMessage, txtPorta};
            /*JOptionPane possibilita a criação de uma caixa de dialogo padrão 
            que ou solicita um valor para o usuário ou retorna uma informação.*/
            JOptionPane.showMessageDialog(null, texts);
            server = new ServerSocket(Integer.parseInt(txtPorta.getText())); //captura o texto da porta e converte para integer e passa por parametro par a classe ServerSocket
            clientes = new ArrayList<BufferedWriter>(); // criando um arraylist do tipo BufferedWriter
            JOptionPane.showMessageDialog(null, "Servidor ativo na porta: "
                    + txtPorta.getText());

            /*Condicional de quando for verdadeira ele irá startar o servidor
              e escrevera as mensagens de aguardo, e quando o cliente conectar
              ira aparecer uma mensagem informando a conexão*/
            while (true) {
                System.out.println("Aguardando conexão...");
                Socket con = server.accept(); // se comunicação não for close ou não vinculada retorna conexão valida
                System.out.println("Cliente conectado...");
                Thread t = new Servidor(con); // criando uma thread para permitir a liberação de comunicação individual do cliente e servidor
                t.start(); // metodo para estartar a comunicação cliente/servidor
            }

        } catch (Exception e) {

            e.printStackTrace();//traça o catch para apresentar o erro caso ocorra
        }
    }// Fim do método main
} //Fim da classe

