import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Project name(项目名称)：Swing文件选择器
 * Package(包名): PACKAGE_NAME
 * Class(类名): test
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/12/4
 * Time(创建时间)： 20:16
 * Version(版本): 1.0
 * Description(描述)： 文件选择器
 * 文件选择器为用户能够操作系统文件提供了桥梁。swing 中使用 JFileChooser 类实现文件选择器，该类常用的构造方法如下。
 * JFileChooser()：创建一个指向用户默认目录的 JFileChooser。
 * JFileChooser(File currentDirectory)：使用指定 File 作为路径来创建 JFileChooser。
 * JFileChooser(String currentDirectoryPath)：创建一个使用指定路径的 JFileChooser。
 * JFileChooser(String currentDirectoryPath, FileSystemView fsv)：使用指定的当前目录路径和 FileSystem View 构造一个 JFileChooser。
 * JFileChooser 类的常用方法如下所示。
 * int showOpenDialog(Component parent)：弹出打开文件对话框。
 * int showSaveDialog(Component parent)：弹出保存文件对话框。
 * showOpenDialog() 方法将返回一个整数，可能取值情况有 3 种：JFileChooser.CANCEL—OPTION、JFileChooser.APPROVE_OPTION
 * 和 JFileChooser.ERROR_OPTION，分别用于表示单击“取消”按钮退出对话框，无文件选取、正常选取文件和发生错误或者对话框已被解除而退出对话框。
 * 因此在文本选择器交互结束后，应进行判断是否从对话框中选择了文件，然后根据返回值情况进行处理。
 */

public class test
{
    private JLabel label = new JLabel("所选文件路径：");
    private JTextField jTextField = new JTextField(25);
    private JButton button = new JButton("浏览");
    private JButton button1 = new JButton("保存");
    private JButton button2 = new JButton("编辑模式");
    boolean isEditable = true;

    public test()
    {
        JFrame jFrame = new JFrame("文件选择测试-文件编辑器");
        jFrame.setSize(1280, 720);
        jFrame.setLocation(1920 / 2 - 640, 1080 / 2 - 360);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel jPanel = new JPanel();
        JTextArea jTextArea = new JTextArea(26, 110);
        JScrollPane jScrollPane = new JScrollPane();
        Font font = new Font("宋体", Font.PLAIN, 19);
        jTextArea.setFont(font);
        button.setBackground(Color.cyan);
        button1.setBackground(Color.cyan);
        button2.setBackground(Color.green);
        jScrollPane.setViewportView(jTextArea);
        jTextArea.setEditable(isEditable);
        jPanel.add(label);
        jPanel.add(jTextField);
        jPanel.add(button);
        jPanel.add(button1);
        jPanel.add(button2);
        //jPanel.add(jTextArea);
        jPanel.add(jScrollPane);
        jFrame.add(jPanel);
        jFrame.setVisible(true);
        button2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (isEditable)
                {
                    button2.setText("只读模式");
                    button2.setBackground(Color.yellow);
                    isEditable = false;
                    jTextArea.setEditable(false);
                    jTextField.setText("当前为只读模式");
                }
                else
                {
                    button2.setText("编辑模式");
                    button2.setBackground(Color.green);
                    isEditable = true;
                    jTextArea.setEditable(true);
                    jTextField.setText("当前为编辑模式");
                }
            }
        });
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser jFileChooser = new JFileChooser(".");
                int result = jFileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    jTextField.setText(jFileChooser.getSelectedFile().toString());
                    File file = jFileChooser.getSelectedFile();


                    FileReader fileReader = null;
                    try                                  //文件流打开，文件读写
                    {
                        fileReader = new FileReader(file);
                        char[] buffer = new char[1024];
                        int count = 0;
                        while ((count = fileReader.read(buffer)) != -1)
                        {
                            jTextArea.append(new String(buffer, 0, count));
                            System.out.println(new String(buffer, 0, count));
                        }

                    }
                    catch (FileNotFoundException e1)      //文件未找到
                    {
                        Toolkit.getDefaultToolkit().beep();
                        System.err.println("文件未找到！！！  " + "\n错误内容：" + e.toString());
                    }
                    catch (Exception e1)                  //其它异常
                    {
                        Toolkit.getDefaultToolkit().beep();
                        e1.printStackTrace();
                    }
                    finally
                    {
                        try                              //关闭流
                        {
                            if (fileReader != null)
                            {
                                fileReader.close();
                            }
                        }
                        catch (NullPointerException e1)    //空指针异常
                        {
                            Toolkit.getDefaultToolkit().beep();
                            System.err.println("文件已经被关闭，无法再次关闭！！！");
                        }
                        catch (Exception e1)              //其它异常
                        {
                            Toolkit.getDefaultToolkit().beep();
                            e1.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toolkit.getDefaultToolkit().beep();
                    jTextField.setText("未选择文件！！！");
                }
            }
        });
        button1.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser jFileChooser = new JFileChooser(".");
                int result = jFileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION)
                {
                    File file = jFileChooser.getSelectedFile();
                    FileWriter fileWriter = null;
                    try                                  //文件流打开，文件读写
                    {
                        fileWriter = new FileWriter(file);
                        fileWriter.write(jTextArea.getText());
                        jTextField.setText("保存成功");
                    }
                    catch (FileNotFoundException e1)      //文件未找到
                    {
                        Toolkit.getDefaultToolkit().beep();
                        System.err.println("文件未找到！！！  " + "\n错误内容：" + e.toString());
                    }
                    catch (Exception e1)                  //其它异常
                    {
                        Toolkit.getDefaultToolkit().beep();
                        e1.printStackTrace();
                    }
                    finally
                    {
                        try                              //关闭流
                        {
                            if (fileWriter != null)
                            {
                                fileWriter.close();
                            }
                        }
                        catch (NullPointerException e1)    //空指针异常
                        {
                            Toolkit.getDefaultToolkit().beep();
                            System.err.println("文件已经被关闭，无法再次关闭！！！");
                        }
                        catch (Exception e1)              //其它异常
                        {
                            Toolkit.getDefaultToolkit().beep();
                            e1.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toolkit.getDefaultToolkit().beep();
                    jTextField.setText("未成功保存！！！");
                }
            }
        });
    }

    public static void main(String[] args)
    {
        new test();
    }
}
