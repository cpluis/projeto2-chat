import javax.swing.*; // classe utilizado para criar uma janela gr�fica.
import java.awt.*; //classe utilizado para criar uma janela gr�fica atrav�s de objetos gr�ficos.
import java.awt.event.ActionEvent; //classe que avisa quando uma a��o ocorre no ambiente gr�fico.
import java.awt.event.ActionListener; //classe que fica monitorando as a��es no ambiente gr�fico.
import java.awt.event.KeyEvent; //classe que identifica quando uma tecla esta em a��o.
import java.awt.event.KeyListener; //classe KeyListener para escuta
import java.io.*; //classe que prov� entrada e sa�da atrav�s do stream de dados.
import java.net.Socket; //classe soquete que permite � conex�o atrav�s de socket.

public class Cliente extends JFrame implements ActionListener, KeyListener { // classe p�blica utulizada extender a fun��o de teclas no painel criado com JFrame com a��o de ficar monitorando as a��es e telcas pressionadas.


    private static final long serialVersionUID = 1L; // uma classe que somente � v�sivel fora da pr�pia classe. O Static final long serialVersionUID � para identificar uma classe serializ�vel, com uma linha.
    private JTextArea texto; // declara��o da variavel do tipo JTextArea
    private JTextField txtMsg; // declara��o da variavel do tipo JTextField
    private JButton btnSend; // declara��o da variavel do tipo JButton
    private JButton btnSair; // declara��o da variavel do tipo JButton
    private JLabel lblHistorico; //  declara��o da variavel do tipo JLabel
    private JLabel lblMsg; //  declara��o da variavel do tipo  JLabel
    private JPanel pnlContent;  // declara��o da variavel do tipo JPanel
    private Socket socket;  // declara��o da variavel do tipo Socket
    private OutputStream ou ; // declara��o da variavel do tipo OutputStream
    private Writer ouw; //  declara��o da variavel do tipo Writer
    private BufferedWriter bfw; // declara��o da variavel do tipo BufferedWriter
    private JTextField txtIP; // declara��o da variavel do tipo JTextField
    private JTextField txtPorta; // declara��o da variavel do tipo JTextField
    private JTextField txtNome; // declara��o da variavel do tipo JTextField

    public Cliente() throws IOException { // classe para assinar o m�todo.
        JLabel lblMessage = new JLabel("Verificar!"); // definir as orienta��es da �rea de visualiza��o.
        txtIP = new JTextField("127.0.0.1"); // vari�vel que ir� armazenar o endere�o do servidor.
        txtPorta = new JTextField("12345"); // vari�vel que ir� armazenar o endere�o da porta do servidor.
        txtNome = new JTextField("Cliente"); // vari�vel que ir� armazenar o nome do cliente.
        Object[] texts = {lblMessage, txtIP, txtPorta, txtNome }; // defini��o dos objetos com os parametros que ser�o apresentados na cixa de idalogo.
        JOptionPane.showMessageDialog(null, texts);// ir� exibir uma mensagem objeto com os atributos definidos na linha a cima.
        pnlContent = new JPanel(); // componente para adaotar a barra de roalgem na interface.
        texto              = new JTextArea(10,20); // �rea horizontal e vertical do campo texto.
        texto.setEditable(false); // torna a edi��o do campo texto desabilitada.
        texto.setBackground(new Color(240,240,240)); // define as cores que ser�o utilizadas na janela de fundo e seu tamanho.
        txtMsg                       = new JTextField(20); //define o tamanho do campo em 20 para msg.
        lblHistorico     = new JLabel("Hist�rico"); // cria o menu Hist�rico.
        lblMsg        = new JLabel("Mensagem"); // cria o menu Mensagem.
        btnSend                     = new JButton("Enviar"); // bot�o que executa a fun��o Enviar.
        btnSend.setToolTipText("Enviar Mensagem"); // executa a fun��o Enviar Mensagem.
        btnSair = new JButton("Sair"); // cria��o do bot�o com o texto Sair.
        btnSair.setToolTipText("Sair do Chat"); //colocando texto de apresenta��o sair do chat no btnSair.
        btnSend.addActionListener(this); // executa a a��o de monitoramento no bot�o.
        btnSair.addActionListener(this); // executa a a��o de monitoramento no bot�o.
        btnSend.addKeyListener(this); // executa a a��o de monitoramento no bot�o Enviar Mensagem. por uma tecla.
        txtMsg.addKeyListener((KeyListener) this);  // executa a a��o de monitoramento das teclas pressionadas para fun��o msg. 
        JScrollPane scroll = new JScrollPane(texto); //cria��o do objeto scroll define a barra de rolagem.
        texto.setLineWrap(true); // define a quebra de linha.
        pnlContent.add(lblHistorico); // define a apresenta��o para o Hist�rico.
        pnlContent.add(scroll); // define a apresenta��o da barra de rolage,
        pnlContent.add(lblMsg); // define a apresenta��o para o r�tulo Msg.
        pnlContent.add(txtMsg); // define a apresenta��o para o texto dentro do Msg.
        pnlContent.add(btnSair); // define a apresenta��o para o bot�o Sair.
        pnlContent.add(btnSend); // define a apresenta��o para o bot�o Enviar.
        pnlContent.setBackground(Color.LIGHT_GRAY); // define a cor da tela de fundo como cinza claro.
        texto.setBorder(BorderFactory.createEtchedBorder(Color.BLUE,Color.BLUE)); // define a apresenta��o para texto com a cor azul e sua borda como azul.
        txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE)); // / define a apresenta��o para Msg com a cor azul e sua borda como azul.
        setTitle(txtNome.getText()); // define o T�tulo.
        setContentPane(pnlContent); // define os pain�is que ser�o mostrados.
        setLocationRelativeTo(null); // define a localiza��o como nula.
        setResizable(false); // deixa desabilitada a fun��o de redimensionameto da janela.
        setSize(250,330); // define o tamanho da tela para 250 por 330.
        setVisible(true); // define como vs�vel.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // executa a a��o para finalizar os processos do painel.
    }

    public void conectar() throws IOException{ // executa a a��o de conex�o ao servidor atrav�s do m�todo conectar.

        socket = new Socket(txtIP.getText(),Integer.parseInt(txtPorta.getText())); // local onde grava as informa��es do endere�o do servidor e porta.
        ou = socket.getOutputStream(); // local onde ocorre a sa�da do socket.
        ouw = new OutputStreamWriter(ou); // local onde incia a sa�da da escrita do strea.
        bfw = new BufferedWriter(ouw); // local onde armazena escreve a informa��o no buffer.
        bfw.write(txtNome.getText()+"\r\n"); // local onde escreve a informa��o da vari�vel Nome.
        bfw.flush(); //faz a limpeza do buffer de escrita.
    }

    public void enviarMensagem(String msg) throws IOException{ //executa a fun��o de enviar e converte a informa��o numa string.

        if(msg.equals("Sair")){ // executa a a��o se msg for Sair.
            bfw.write("Desconectado \r\n"); // armazena no buffer de escrita "Desconectado.
            texto.append("Desconectado \r\n"); // acrescenta na var�avel texto e exibie "Desconectado.
        }else{ //condi��o sen�o
            bfw.write(msg+"\r\n"); // buffer de escrita salva na vari�vel msg e formata a mensagem com quebra de linha e espa�o.
            texto.append( txtNome.getText() + " diz -> " +         txtMsg.getText()+"\r\n"); // Capturando o nome do cliente e concatenando com a palavra diz-> e a mensagem enviada
        }
        bfw.flush(); //faz a limpeza do buffer de leitura.
        txtMsg.setText(""); // limpa a informa��o que estava na vari�vel msg.
    }
    public void escutar() throws IOException{ //manda a a��o escutar ser executada.

        InputStream in = socket.getInputStream(); // entrada de bytes.
        InputStreamReader inr = new InputStreamReader(in); //faz a leitura da entrada do stream.
        BufferedReader bfr = new BufferedReader(inr); // faz a leitura do stream de sa�da.
        String msg = ""; // local onde ser� feita a convers�o msg = "".

        while(!"Sair".equalsIgnoreCase(msg)) //condi��o quando Sair for ignorada.

            if(bfr.ready()){ // condi��o para fazer a leitura.
                msg = bfr.readLine(); //var�vel msg que faz a leitura da linha.
               //if de verifica��o se cli
                if(msg.equals("Sair"))
                    texto.append("Servidor caiu! \r\n"); // exibe a mensagem "Servidor caiu".
                else
                    texto.append(msg+"\r\n"); // exibe uma mensagem com linhas e quebra de linha.
            }
    }

    public void sair() throws IOException{ //manda uma a��o ser excutada.
        enviarMensagem("Sair"); // envia a mensagem Sair.
        bfw.close(); // encerra o buffer de escrita.
        ouw.close(); // encerra a escrita de dados.
        ou.close(); // encerra o socket de stream.
        socket.close(); //encerra a conex�o do socket.
    }

    @Override
    public void actionPerformed(ActionEvent e) { //implementa a classe de a��o de eventos.

        try {
            if(e.getActionCommand().equals(btnSend.getActionCommand())) //executa a a��o de enviar quando pressionado o bot�o enviar.
            enviarMensagem(txtMsg.getText()); // execu��o do envia a mensagem
            else
            if(e.getActionCommand().equals(btnSair.getActionCommand())) // executa a a��o de sair quando pressionado o bot�o sair.
                sair(); //chamada do metodo sair().
        } catch (IOException e1) { // encerra a verifica��o do bloco de declara��es.
            // TODO Auto-generated catch block
            e1.printStackTrace(); //imprime a descri��o da verifica��o do bloco de declara��es em caso de erro.
        }
    }

    @Override // verificar se h� um m�otodo sobreposto
    public void keyPressed(KeyEvent e) { // classe para verificar se uma tecla foi pressionada.
        if(e.getKeyCode() == KeyEvent.VK_ENTER){ // verifica se uma tecla ENTER foi pressionada.
            try { // iniciar verifica��o de um bloco de declara��es.
                enviarMensagem(txtMsg.getText()); //
            } catch (IOException e1) { // encerra a verifica��o do bloco de declara��es.
                // TODO Auto-generated catch block
                e1.printStackTrace(); //imprime a descri��o da verifica��o do bloco de declara��es em caso de erro.
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override // verificar se h� um m�todo sobreposto
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }

    public static void main(String []args) throws IOException{
        Cliente app = new Cliente(); // cria��o do objeto cliente de nome app
        app.conectar();  //objeto chamando o metodo conectar
        app.escutar();  //objeto chamando o metodo escutar
    }
}