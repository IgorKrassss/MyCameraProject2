package com.opencvcamera;
// Java Program to take a Snapshot from System Camera
// using OpenCV
// Importing openCV modules
//package com.opencvcamera;
// importing swing and awt classes
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Importing date class of sql package
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
// Importing VideoCapture class
// This class is responsible for taking screenshot
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

// Class - Swing Class
public class MyCameraProject2 extends JFrame {
    // Camera screen
    private final JLabel cameraScreen;
    private boolean clicked = false ;
    public MyCameraProject2()
    {
// Designing UI
        setLayout( null );
        cameraScreen = new JLabel();
        //cameraScreen.setBounds( 0 , 0 , 640 , 480 );
        //cameraScreen.setBounds( 22 , 0 , 700 , 520 );
        cameraScreen.setBounds( 130 , 0 , 1024 , 800 );
        add(cameraScreen);
        // Button for image capture
        JButton btnCapture = new JButton("СНИМОК");
        //btnCapture.setBounds( 300 , 480 , 80 , 40 );
        btnCapture.setBounds( 1200 , 40 , 200 , 140 );
        //btnCapture.setBounds( 10 , 40 , 80 , 40 );
        add(btnCapture);
        btnCapture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clicked = true ;
            }
        });
        //setSize( new Dimension( 640 , 560 ));
        //setSize( new Dimension( 700 , 600 ));
        setSize( new Dimension( 2048 , 1536 )); //2048  1536
        setLocationRelativeTo( null );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible( true );
    }
    // Creating a camera
    public void startCamera() throws InterruptedException {
        // Start camera
        VideoCapture capture = new VideoCapture(0);
        // задаем пиксели в изображении видеокамеры
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 2048);//1024
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 1536);//768
        // Store image as 2D matrix
        Mat image = new Mat();
        byte [] imageData;
        ImageIcon icon;
        while ( true ) {
// read image to matrix
            capture.read(image);
// convert matrix to byte
            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode( ".jpg" , image, buf);
            imageData = buf.toArray();
            Thread.sleep(10); // 10 кадров в секунду
// Add to JLabel
            icon = new ImageIcon(imageData);
            cameraScreen.setIcon(icon);
// Capture and save to file
            if (clicked) {
// prompt for enter image name
                String name = JOptionPane.showInputDialog(
                        this , "Enter image name" );
                if (name == null ) {
                    name = new SimpleDateFormat(
                            "yyyy-mm-dd-hh-mm-ss" )
                            .format( new Date(
                                    HEIGHT, WIDTH, getX()));
                }
// Write to file
                Imgcodecs.imwrite( "C:/images/" + name + ".jpg" ,
                        image);
                clicked = false ;
            }
        }
    }
    // Main driver method
    public static void main(String[] args)
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        EventQueue.invokeLater( new Runnable() {
            // Overriding existing run() method
            @Override public void run()
            {
                final MyCameraProject2 camera = new MyCameraProject2();
// Start camera in thread
                new Thread( new Runnable() {
                    @Override public void run()
                    {
                        try {
                            camera.startCamera();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
     }
}


