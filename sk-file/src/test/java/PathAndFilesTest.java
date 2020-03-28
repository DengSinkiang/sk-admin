import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @Description:
 * @Author: dengxj29231
 * @Date: 2020/3/12 14:10
 * @CopyRight: 2020 hundsun all rights reserved.
 */
public class PathAndFilesTest {
    /**
     * 创建Path
     */
    @Test
    public void createPath() throws URISyntaxException, MalformedURLException {
        //1.Paths
        Path path = Paths.get("F:/测试数据.csv");
        System.out.println(path.getFileName());
        Path path1 = Paths.get(new URI("file:///f:/测试数据.csv"));

        //2.FileSystems
        Path path2 = FileSystems.getDefault().getPath("F:/测试数据.csv");

        //3.File
        Path path3 = new File("F:/测试数据.csv").toPath();
    }


    /**
     * 创建一个空文件/文件夹
     */
    @Test
    public void create() throws IOException {
        //文件夹
        Path path = Paths.get("F:/hello5/hello55/hello555");
        if (!Files.exists(path)) {//如果不存在
            //Files.createDirectory(path);
            //创建多个目录
            Files.createDirectories(path);
        }

        //文件
        Path path1 = Paths.get("F:/helloFile.txt");
        if (!Files.exists(path1)) {//如果不存在
            Files.createFile(path1);
        }
    }

    /**
     * 文件属性
     */
    @Test
    public void getFileProperties() throws IOException {
        Path path = Paths.get("F:/helloFile.txt");
        System.out.println(Files.getLastModifiedTime(path));//最后修改时间:2019-05-22T02:52:45.625094Z
        System.out.println(Files.getOwner(path));//拥有者:DESKTOP-GE36VVD\87772 (User)
        //System.out.println(Files.getPosixFilePermissions(path));//权限,非admin可能会报错
        System.out.println(Files.size(path));//文件大小: 34207517
    }

    /**
     * 读取一个文本文件
     */
    @Test
    public void readText() throws IOException {
        Path path = Paths.get("F:/helloFile.txt");
        //通过bufferedReader读取
        BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);///该文件编码是什么newBufferedReader就必须指定什么字符集,否则报错

        StringBuilder sb = new StringBuilder();
        String tempString;
        while ((tempString = bufferedReader.readLine()) != null) {
            sb.append(tempString).append("\n");
        }
        System.out.println(sb);


        //通过Files方法readAllLines
        List<String> strings = Files.readAllLines(path);
        strings.forEach(System.out::println);
    }

    /**
     * 拿到文件输入流
     *
     * @throws IOException
     */
    @Test
    public void getInputStream() throws IOException {
        Path path = Paths.get("F:/helloFile.txt");
        InputStream inputStream = Files.newInputStream(path);

        //转换字符流后在包装成缓冲流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        StringBuilder sb = new StringBuilder();
        String tempString = null;
        while ((tempString = bufferedReader.readLine()) != null) {
            sb.append(tempString).append("\n");
        }
        System.out.println(sb);
    }

    /**
     * 文件写操作
     */
    @Test
    public void writeFile() throws IOException {
        Path path = Paths.get("F:/helloFile.txt");

        //获取写入流
        BufferedWriter bufferedWriter = Files.newBufferedWriter(path);

        //执行写入操作
        String str = "write file test";
        bufferedWriter.write(str);

        //关闭资源
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * 遍历一个文件夹
     */
    @Test
    public void traverseDirectory() throws IOException {
        Path path = Paths.get("F:/hello");
        Stream<Path> list = Files.list(path);
        list.forEach(p -> {
            System.out.println(p.getFileName());
        });
    }

    /**
     * 遍历文件树
     */
    @Test
    public void traverseTree() throws IOException {
        Path path = Paths.get("F:/hello");
        Stream<Path> walk = Files.walk(path);
        walk.forEach(path1 -> {
//      System.out.println(path1.getRoot());//根目录
            System.out.println(path1.getFileName());//文件名
//      System.out.println(path1.getParent());//上级目录
//      System.out.println(path1.getFileSystem());//文件系统
        });
        //还有种方式Files.walkFileTree()
    }

    /**
     * 文件复制
     */
    @Test
    public void copyFile() throws IOException {
        Path src = Paths.get("F:/helloFile.txt");
        Path dest = Paths.get("F:/hello/helloFile1.txt");
        Files.copy(src, dest);
    }

    /**
     * 读取权限见上面示例，设置权限
     */
    @Test
    public void writePermission() throws IOException {
        Path path = Paths.get("F:/hello/helloFile1.txt");
        Set<PosixFilePermission> permissionSet = new HashSet<>();
        permissionSet.add(PosixFilePermission.GROUP_WRITE);
        permissionSet.add(PosixFilePermission.OWNER_EXECUTE);
        Files.setPosixFilePermissions(path, permissionSet);
    }

    /**
     * 判断方法
     * @throws IOException
     */
    @Test
    public void judge() throws IOException {
        Path path1 = Paths.get("f:\\test", "Copy测试数据.csv");
        Path path2 = Paths.get("f:\\测试数据.csv");
//		boolean exists(Path path, LinkOption … opts) : 判断文件是否存在
        System.out.println(Files.exists(path2, LinkOption.NOFOLLOW_LINKS));//true

//		boolean isDirectory(Path path, LinkOption … opts) : 判断是否是目录
        //不要求此path对应的物理文件存在。
        System.out.println(Files.isDirectory(path1, LinkOption.NOFOLLOW_LINKS));//false

//		boolean isRegularFile(Path path, LinkOption … opts) : 判断是否是文件

//		boolean isHidden(Path path) : 判断是否是隐藏文件
        //要求此path对应的物理上的文件需要存在。才可判断是否隐藏。否则，抛异常。
//		System.out.println(Files.isHidden(path1));

//		boolean isReadable(Path path) : 判断文件是否可读
        System.out.println(Files.isReadable(path1));//true
//		boolean isWritable(Path path) : 判断文件是否可写
        System.out.println(Files.isWritable(path1));//true
//		boolean notExists(Path path, LinkOption … opts) : 判断文件是否不存在
        System.out.println(Files.notExists(path1, LinkOption.NOFOLLOW_LINKS));//false
    }


    /**
     * StandardOpenOption.READ:表示对应的Channel是可读的。
     * StandardOpenOption.WRITE：表示对应的Channel是可写的。
     * StandardOpenOption.CREATE：如果要写出的文件不存在，则创建。如果存在，忽略
     * StandardOpenOption.CREATE_NEW：如果要写出的文件不存在，则创建。如果存在，抛异常
     */
    @Test
    public void ioStream() throws IOException {
//        Path path1 = Paths.get("f:\\hello", "copyTest.txt");
//
////		InputStream newInputStream(Path path, OpenOption…how):获取 InputStream 对象
//        InputStream inputStream = Files.newInputStream(path1, StandardOpenOption.READ);
//
////		OutputStream newOutputStream(Path path, OpenOption…how) : 获取 OutputStream 对象
//        OutputStream outputStream = Files.newOutputStream(path1, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
//
//
////		SeekableByteChannel newByteChannel(Path path, OpenOption…how) : 获取与指定文件的连接，how 指定打开方式。
//        SeekableByteChannel channel = Files.newByteChannel(path1, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

//		DirectoryStream<Path>  newDirectoryStream(Path path) : 打开 path 指定的目录
        Path path2 = Paths.get("f:\\hello");
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path2);
        for (Path path : directoryStream) {
            System.out.println(path);
        }
    }
}