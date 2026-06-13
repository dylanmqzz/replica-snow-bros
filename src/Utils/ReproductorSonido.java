package Utils;

import javax.sound.sampled.*;
import java.io.File;

public class ReproductorSonido {
    protected static ReproductorSonido instancia;
    protected Clip musicaFondo;

    private ReproductorSonido() {
        
    }

    public static ReproductorSonido getInstancia() {
        if (instancia == null)
            instancia = new ReproductorSonido();
        return instancia;
    }

    public void reproducirSonido(String archivo) {
        try {
            File file = new File(archivo);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            AudioFormat baseFormat = audioIn.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100,                               
                    16,                                  
                    baseFormat.getChannels(),            
                    baseFormat.getChannels() * 2,        
                    44100,                               
                    false                                
            );
            AudioInputStream decodedAudio = AudioSystem.getAudioInputStream(decodedFormat, audioIn);
            Clip clip = AudioSystem.getClip();
            clip.open(decodedAudio);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("⚠️ Formato de audio no soportado: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("⚠️ Línea de audio no disponible: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reproducirMusicaLoop(String archivo) {
        detenerMusica();
        try {
            File file = new File(archivo);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            AudioFormat baseFormat = audioIn.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100,
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    44100,
                    false
            );
            AudioInputStream decodedAudio = AudioSystem.getAudioInputStream(decodedFormat, audioIn);
            musicaFondo = AudioSystem.getClip();
            musicaFondo.open(decodedAudio);
            musicaFondo.loop(Clip.LOOP_CONTINUOUSLY); 
            musicaFondo.start();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("⚠️ Formato de audio no soportado: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("⚠️ Línea de audio no disponible: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void detenerMusica() {
        if (musicaFondo != null && musicaFondo.isRunning()) {
            musicaFondo.stop();
            musicaFondo.close();
            musicaFondo = null;
        }
    }

    public void pausarMusica() {
        if (musicaFondo != null && musicaFondo.isRunning())
            musicaFondo.stop();
    }

    public void reanudarMusica() {
        if (musicaFondo != null && !musicaFondo.isRunning())
            musicaFondo.start();
    }

    public void setVolumenMusica(float volumen) {
        if (musicaFondo != null && musicaFondo.isOpen()) {
            try {
                FloatControl gainControl = (FloatControl) musicaFondo.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(volumen) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
            } catch (Exception e) {
                System.err.println("⚠️ No se pudo ajustar el volumen: " + e.getMessage());
            }
        }
    }

}
