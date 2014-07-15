package driveTools;


import javax.swing.*;
import java.awt.*;

import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import static javafx.concurrent.Worker.State.FAILED;

public class WebViewTrial extends JFrame{
	private static final long serialVersionUID = 42L;
	private final JFXPanel jfxPanel = new JFXPanel();
    private WebEngine engine;
    
    private final JPanel panel = new JPanel(new BorderLayout());

    public WebViewTrial() {
        super();
        initComponents();
    }

    private void initComponents() {
        createScene();
<<<<<<< HEAD
        panel.add(new JLabel("I like to write stupid shit"), BorderLayout.NORTH);
        panel.add(jfxPanel, BorderLayout.CENTER);
        panel.add(new JButton("I do nothing... :("), BorderLayout.SOUTH);
        panel.add(new JLabel("..Blubb!"), BorderLayout.EAST);
        panel.add(new JLabel("Blibla.."), BorderLayout.WEST);
=======
        panel.add(jfxPanel, BorderLayout.CENTER);
        
>>>>>>> 9d54729123a10fac1106286540c9ca0ffaa47f4c
        getContentPane().add(panel);

        setPreferredSize(new Dimension(1024, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

	
	public void loadURL(final String url) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String tmp = toURL(url);

                if (tmp == null) {
                    tmp = toURL("http://" + url);
                }
<<<<<<< HEAD

=======
>>>>>>> 9d54729123a10fac1106286540c9ca0ffaa47f4c
                engine.load(tmp);
            }
        });
    }

    private static String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            return null;
        }
    }
    
    private void createScene() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                WebView view = new WebView();
                engine = view.getEngine();
                engine.getLoadWorker()
                .exceptionProperty()
                .addListener(new ChangeListener<Throwable>() {

                    public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
                        if (engine.getLoadWorker().getState() == FAILED) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    JOptionPane.showMessageDialog(
                                    panel,
                                    (value != null)
                                    ? engine.getLocation() + "\n" + value.getMessage()
                                    : engine.getLocation() + "\nUnexpected error.",
                                    "Loading error...",
                                    JOptionPane.ERROR_MESSAGE);
                                }
                            });
                        }
                    }
                });

                jfxPanel.setScene(new Scene(view));
            }
        });

    }
    
<<<<<<< HEAD
    public static void main(String[] args) {
=======
    /*public static void main(String[] args) {
>>>>>>> 9d54729123a10fac1106286540c9ca0ffaa47f4c
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                WebViewTrial browser = new WebViewTrial();
                browser.setVisible(true);
                browser.loadURL("https://accounts.google.com/o/oauth2/auth?access_type=online&approval_prompt=auto&client_id=448650047220-m72n2idc18dcv7p2hpdevkjpnkhfhnf3.apps.googleusercontent.com&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code&scope=https://www.googleapis.com/auth/drive");
<<<<<<< HEAD
                //browser.loadURL("www.google.de");
            }
        });
    }
=======
            }
        });
    }*/
>>>>>>> 9d54729123a10fac1106286540c9ca0ffaa47f4c
}

