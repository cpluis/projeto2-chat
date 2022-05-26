import javax.swing.*; // classe utilizado para criar uma janela gráfica.
import java.awt.*; //classe utilizado para criar uma janela gráfica através de objetos gráficos.
import java.awt.event.ActionEvent; //classe que avisa quando uma ação ocorre no ambiente gráfico.
import java.awt.event.ActionListener; //classe que fica monitorando as ações no ambiente gráfico.
import java.awt.event.KeyEvent; //classe que identifica quando uma tecla esta em ação.
import java.awt.event.KeyListener; //classe KeyListener para escuta
import java.io.*; //classe que provê entrada e saída através do stream de dados.
import java.net.Socket; //classe soquete que permite à conexão através de socket.

public class Cliente extends JFrame implements ActionListener, KeyListener { // classe pública utulizada extender a função de teclas no painel criado com JFrame com ação de ficar monitorando as ações e telcas pressionadas.


    private static final long serialVersionUID = 1L; // uma classe que somente é vísivel fora da própia classe. O Static final long serialVersionUID é para identificar uma classe serializável, com uma linha.
    private JTextArea texto; // declaração da variavel do tipo JTextArea
    private JTextField txtMsg; // declaração da variavel do tipo JTextField
    private JButton btnSend; // declaração da variavel do tipo JButton
    private JButton btnSair; // declaração da variavel do tipo JButton
    private JLabel lblHistorico; //  declaração da variavel do tipo JLabel
    private JLabel lblMsg; //  declaração da variavel do tipo  JLabel
    private JPanel pnlContent;  // declaração da variavel do tipo JPanel
    private Socket socket;  // declaração da variavel do tipo Socket
    private OutputStream ou ; // declaração da variavel do tipo OutputStream
    private Writer ouw; //  declaração da variavel do tipo Writer
    private BufferedWriter bfw; // declaração da variavel do tipo BufferedWriter
    private JTextField txtIP; // declaração da variavel do tipo JTextField
    private JTextField txtPorta; // declaração da variavel do tipo JTextField
    private JTextField txtNome; // declaração da variavel do tipo JTextField

    public Cliente() throws IOException { // classe para assinar o método.
        JLabel lblMessage = new JLabel("Verificar!"); // definir as orientações da área de visualização.
        txtIP = new JTextField("127.0.0.1"); // variável que irá armazenar o endereço do servidor.
        txtPorta = new JTextField("12345"); // variável que irá armazenar o endereço da porta do servidor.
        txtNome = new JTextField("Cliente"); // variável que irá armazenar o nome do cliente.
        Object[] texts = {lblMessage, txtIP, txtPorta, txtNome }; // definição dos objetos com os parametros que serão apresentados na cixa de idalogo.
        JOptionPane.showMessageDialog(null, texts);// irá exibir uma mensagem objeto com os atributos definidos na linha a cima.
        pnlContent = new JPanel(); // componente para adaotar a barra de roalgem na interface.
        texto              = new JTextArea(10,20); // área horizontal e vertical do campo texto.
        texto.setEditable(false); // torna a edição do campo texto desabilitada.
        texto.setBackground(new Color(240,240,240)); // define as cores que serão utilizadas na janela de fundo e seu tamanho.
        txtMsg                       = new JTextField(20); //define o tamanho do campo em 20 para msg.
        lblHistorico     = new JLabel("Histórico"); // cria o menu Histórico.
        lblMsg        = new JLabel("Mensagem"); // cria o menu Mensagem.
        btnSend                     = new JButton("Enviar"); // botão que executa a função Enviar.
        btnSend.setToolTipText("Enviar Mensagem"); // executa a função Enviar Mensagem.
        btnSair = new JButton("Sair"); // criação do botão com o texto Sair.
        btnSair.setToolTipText("Sair do Chat"); //colocando texto de apresentação sair do chat no btnSair.
        btnSend.addActionListener(this); // executa a ação de monitoramento no botão.
        btnSair.addActionListener(this); // executa a ação de monitoramento no botão.
        btnSend.addKeyListener(this); // executa a ação de monitoramento no botão Enviar Mensagem. por uma tecla.
        txtMsg.addKeyListener((KeyListener) this);  // executa a ação de monitoramento das teclas pressionadas para função msg. 
        JScrollPane scroll = new JScrollPane(texto); //criação do objeto scroll define a barra de rolagem.
        texto.setLineWrap(true); // define a quebra de linha.
        pnlContent.add(lblHistorico); // define a apresentação para o Histórico.
        pnlContent.add(scroll); // define a apresentação da barra de rolage,
        pnlContent.add(lblMsg); // define a apresentação para o rótulo Msg.
        pnlContent.add(txtMsg); // define a apresentação para o texto dentro do Msg.
        pnlContent.add(btnSair); // define a apresentação para o botão Sair.
        pnlContent.add(btnSend); // define a apresentação para o botão Enviar.
        pnlContent.setBackground(Color.LIGHT_GRAY); // define a cor da tela de fundo como cinza claro.
        texto.setBorder(BorderFactory.createEtchedBorder(Color.BLUE,Color.BLUE)); // define a apresentação para texto com a cor azul e sua borda como azul.
        txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE)); // / define a apresentação para Msg com a cor azul e sua borda como azul.
        setTitle(txtNome.getText()); // define o Título.
        setContentPane(pnlContent); // define os painéis que serão mostrados.
        setLocationRelativeTo(null); // define a localização como nula.
        setResizable(false); // deixa desabilitada a função de redimensionameto da janela.
        setSize(250,330); // define o tamanho da tela para 250 por 330.
        setVisible(true); // define como vsível.
        setDefaultCloseOperation(EXIT_ON_CLOSE); // executa a ação para finalizar os processos do painel.
    }

    public void conectar() throws IOException{ // executa a ação de conexão ao servidor através do método conectar.

        socket = new Socket(txtIP.getText(),Integer.parseInt(txtPorta.getText())); // local onde grava as informações do endereço do servidor e porta.
        ou = socket.getOutputStream(); // local onde ocorre a saída do socket.
        ouw = new OutputStreamWriter(ou); // local onde incia a saída da escrita do strea.
        bfw = new BufferedWriter(ouw); // local onde armazena escreve a informação no buffer.
        bfw.write(txtNome.getText()+"\r\n"); // local onde escreve a informação da variável Nome.
        bfw.flush(); //faz a limpeza do buffer de escrita.
    }

    public void enviarMensagem(String msg) throws IOException{ //executa a função de enviar e converte a informação numa string.

        if(msg.equals("Sair")){ // executa a ação se msg for Sair.
            bfw.write("Desconectado \r\n"); // armazena no buffer de escrita "Desconectado.
            texto.append("Desconectado \r\n"); // acrescenta na varíavel texto e exibie "Desconectado.
        }else{ //condição senão
            bfw.write(msg+"\r\n"); // buffer de escrita salva na variável msg e formata a mensagem com quebra de linha e espaço.
            texto.append( txtNome.getText() + " diz -> " +         txtMsg.getText()+"\r\n"); // Capturando o nome do cliente e concatenando com a palavra diz-> e a mensagem enviada
        }
        bfw.flush(); //faz a limpeza do buffer de leitura.
        txtMsg.setText(""); // limpa a informação que estava na variável msg.
    }
    public void escutar() throws IOException{ //manda a ação escutar ser executada.

        InputStream in = socket.getInputStream(); // entrada de bytes.
        InputStreamReader inr = new InputStreamReader(in); //faz a leitura da entrada do stream.
        BufferedReader bfr = new BufferedReader(inr); // faz a leitura do stream de saída.
        String msg = ""; // local onde será feita a conversão msg = "".

        while(!"Sair".equalsIgnoreCase(msg)) //condição quando Sair for ignorada.

            if(bfr.ready()){ // condição para fazer a leitura.
                msg = bfr.readLine(); //varável msg que faz a leitura da linha.
               //if de verificação se cli
                if(msg.equals("Sair"))
                    texto.append("Servidor caiu! \r\n"); // exibe a mensagem "Servidor caiu".
                else
                    texto.append(msg+"\r\n"); // exibe uma mensagem com linhas e quebra de linha.
            }
    }

    public void sair() throws IOException{ //manda uma ação ser excutada.
        enviarMensagem("Sair"); // envia a mensagem Sair.
        bfw.close(); // encerra o buffer de escrita.
        ouw.close(); // encerra a escrita de dados.
        ou.close(); // encerra o socket de stream.
        socket.close(); //encerra a conexão do socket.
    }

    @Override
    public void actionPerformed(ActionEvent e) { //implementa a classe de ação de eventos.

        try {
            if(e.getActionCommand().equals(btnSend.getActionCommand())) //executa a ação de enviar quando pressionado o botão enviar.
            enviarMensagem(txtMsg.getText()); // execução do envia a mensagem
            else
            if(e.getActionCommand().equals(btnSair.getActionCommand())) // executa a ação de sair quando pressionado o botão sair.
                sair(); //chamada do metodo sair().
        } catch (IOException e1) { // encerra a verificação do bloco de declarações.
            // TODO Auto-generated catch block
            e1.printStackTrace(); //imprime a descrição da verificação do bloco de declarações em caso de erro.
        }
    }

    @Override // verificar se há um méotodo sobreposto
    public void keyPressed(KeyEvent e) { // classe para verificar se uma tecla foi pressionada.
        if(e.getKeyCode() == KeyEvent.VK_ENTER){ // verifica se uma tecla ENTER foi pressionada.
            try { // iniciar verificação de um bloco de declarações.
                enviarMensagem(txtMsg.getText()); //
            } catch (IOException e1) { // encerra a verificação do bloco de declarações.
                // TODO Auto-generated catch block
                e1.printStackTrace(); //imprime a descrição da verificação do bloco de declarações em caso de erro.
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override // verificar se há um método sobreposto
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }

    public static void main(String []args) throws IOException{
        Cliente app = new Cliente(); // criação do objeto cliente de nome app
        app.conectar();  //objeto chamando o metodo conectar
        app.escutar();  //objeto chamando o metodo escutar
    }
}