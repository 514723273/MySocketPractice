import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        //超时时间
        socket.setSoTimeout(3000);

        //发起连接，端口2000；超时时间3000ms
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 2000), 3000);

        System.out.println("已发起服务器连接，并进入后续流程～");
        System.out.println("客户端信息： " + socket.getLocalAddress() + " Port: " + socket.getLocalPort());
        System.out.println("服务器信息： " + socket.getInetAddress() + " Port: " + socket.getPort());

        try {
            //发送接收数据
            todo(socket);
        } catch (Exception e) {
            System.out.println("异常关闭");
        }

        socket.close();
        System.out.println("客户端已退出");
    }

    private static void todo(Socket client) throws IOException {
        //构建键盘输入流
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));

        //得到Socket输出流，并转换为打印流BufferedReader
        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);

        //得到Scoket输入流，并转换为
        InputStream inputStream = client.getInputStream();
        BufferedReader socketBufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        //屏幕输入 input->客户端Socket输出 socketPrintStream->等待服务器回复（服务器输出）-> 客户端Socket输入socketBufferedReader -> 屏幕输出System.out.Println()
        boolean flag = true;
        do {
            //键盘读取一行
            String str = input.readLine();
            //发送给服务器
            socketPrintStream.println(str);
            //从服务区读取一行
            String echo = socketBufferedReader.readLine();

            if("bye".equals(echo)) {
                flag = false;
            } else {
                System.out.println(echo);
            }
        } while(flag);

        //资源释放
        socketPrintStream.close();
        socketBufferedReader.close();
    }
}
