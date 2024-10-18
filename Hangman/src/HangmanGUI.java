import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HangmanGUI extends JFrame {

    // GUI-Komponenten
    private final JTextField buchstabeEingabeFeld;
    private final JLabel wortLabel;
    private final JLabel fehlversucheLabel;
    private final JLabel bildLabel;
    private final JLabel falscheBuchstabenLabel;
    private int fehlversuche;
    private int maxFehlversuche = 9; // Standardwert für maximale Fehlversuche
    private String aktuellesWort;
    private StringBuilder angezeigtesWort;
    private final ArrayList<String> wortListe;
    private final Set<Character> falscheBuchstaben;

    // Konstruktor
    public HangmanGUI() {
        setTitle("Hangman Spiel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 780);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Initialisierung der Variablen und Listen
        wortListe = new ArrayList<>();
        falscheBuchstaben = new HashSet<>();

        // Laden der Wörter aus einer Datei
        try {
            ladeWortAusDatei();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Fehler beim Laden der Wörterdatei: " + e.getMessage());
            System.exit(1);
        }

        // Initialisierung des Spiels und Anzeige des ersten Wortes
        aktuellesWort = zufaelligesWort();  // Wählt ein zufälliges Wort
        angezeigtesWort = new StringBuilder("_".repeat(aktuellesWort.length()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Menüleiste erstellen
        JMenuBar menuBar = new JMenuBar();
        JMenu einstellungenMenu = new JMenu("Einstellungen");
        JMenuItem versucheMenuItem = new JMenuItem("Anzahl der Versuche ändern");
        versucheMenuItem.addActionListener(e -> versucheAendern());
        einstellungenMenu.add(versucheMenuItem);
        menuBar.add(einstellungenMenu);
        setJMenuBar(menuBar);

        // Panel für das Bild
        bildLabel = new JLabel();
        bildAktualisieren(0); // Initiales Bild setzen
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        add(bildLabel, gbc);

        // Panel für die Wortanzeige
        wortLabel = new JLabel(printWordWithSpaces(angezeigtesWort));
        wortLabel.setFont(new Font("Arial", Font.BOLD, 48));  // Größere Schriftgröße
        wortLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0.1;
        add(wortLabel, gbc);

        // Panel für die Fehlversuche
        fehlversucheLabel = new JLabel("Fehlversuche: 0 / " + maxFehlversuche);
        fehlversucheLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        fehlversucheLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weighty = 0.1;
        add(fehlversucheLabel, gbc);

        // Panel für die falschen Buchstaben
        falscheBuchstabenLabel = new JLabel("Falsche Buchstaben: ");
        falscheBuchstabenLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        falscheBuchstabenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weighty = 0.1;
        add(falscheBuchstabenLabel, gbc);

        // Eingabefeld für den Buchstaben und Button
        buchstabeEingabeFeld = new JTextField(2);
        buchstabeEingabeFeld.setFont(new Font("Arial", Font.PLAIN, 24));
        JButton ratenButton = new JButton("Raten");
        ratenButton.setFont(new Font("Arial", Font.PLAIN, 24));
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.weighty = 0.1;
        add(buchstabeEingabeFeld, gbc);

        // Button zum Raten
        gbc.gridx = 1;
        add(ratenButton, gbc);

        // Enter-Taste zur Bestätigung verwenden
        buchstabeEingabeFeld.addActionListener(e -> buchstabeVerarbeiten());
        ratenButton.addActionListener(e -> buchstabeVerarbeiten());

        setVisible(true);
    }

    // Methode zum Laden der Wörter aus einer Datei
    private void ladeWortAusDatei() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/woerter.txt");
        if (inputStream == null) {
            throw new IOException("Datei nicht gefunden: " + "/woerter.txt");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wortListe.add(line.trim().toUpperCase());
            }
        }
    }

    // Methode zum Aktualisieren des Bildes basierend auf der Anzahl der Fehlversuche
    private void bildAktualisieren(int fehlversuch) {
        // Berechnet den Bildindex basierend auf der Anzahl der Fehlversuche und der Anzahl der verfügbaren Bilder
        int letztesBild = 10;
        int bildIndex;

        if (fehlversuch >= maxFehlversuche) {

            // Verwende das letzte Bild, wenn die Fehlversuche die maximale Anzahl erreichen
            bildIndex = letztesBild - 1;
        } else {
            // Berechne den Bildindex basierend auf der Anzahl der Fehlversuche und der maximalen Anzahl der Bilder
            bildIndex = (int) ((double) fehlversuch / maxFehlversuche * (letztesBild - 1));
        }

        // Bild aus dem Ressourcenordner laden und im Bildlabel anzeigen oder eine Fehlermeldung ausgeben
        String pfad = "/images/hangman" + bildIndex + ".png";
        java.net.URL bildURL = getClass().getResource(pfad);
        if (bildURL != null) {
            ImageIcon bild = new ImageIcon(bildURL);
            bildLabel.setIcon(bild);
        } else {
            System.err.println("Bild nicht gefunden: " + pfad);
        }
    }

    // Methode zur Verarbeitung des eingegebenen Buchstabens oder Wortes
    private void buchstabeVerarbeiten() {
        String eingabe = buchstabeEingabeFeld.getText().toUpperCase();
        buchstabeEingabeFeld.setText("");

        // Überprüfen, ob der Benutzer einen gültigen Buchstaben oder ein gültiges Wort eingegeben hat
        if (eingabe.length() == 1 && Character.isLetter(eingabe.charAt(0))) {
            // Einzelner Buchstabe
            char buchstabe = eingabe.charAt(0);
            if (falscheBuchstaben.contains(buchstabe) || angezeigtesWort.toString().contains(String.valueOf(buchstabe))) {
                JOptionPane.showMessageDialog(this, "Dieser Buchstabe wurde bereits verwendet.");
                return;
            }

            // Überprüfen, ob der Buchstabe im Wort enthalten ist
            if (aktuellesWort.contains(String.valueOf(buchstabe))) {
                for (int i = 0; i < aktuellesWort.length(); i++) {
                    if (aktuellesWort.charAt(i) == buchstabe) {
                        angezeigtesWort.setCharAt(i, buchstabe);
                    }
                }
                wortLabel.setText(printWordWithSpaces(angezeigtesWort));
            } else {
                fehlversuche++;
                fehlversucheLabel.setText("Fehlversuche: " + fehlversuche + " / " + maxFehlversuche);
                bildAktualisieren(fehlversuche);
                falscheBuchstaben.add(buchstabe);
                falscheBuchstabenLabel.setText("Falsche Buchstaben: " + falscheBuchstaben);
            }

            // Überprüfen, ob das Wort erraten wurde oder das Spiel vorbei ist
            if (angezeigtesWort.toString().equals(aktuellesWort)) {
                JOptionPane.showMessageDialog(this, "Glückwunsch! Du hast das Wort erraten.");
                neuesSpielStarten();
            } else if (fehlversuche >= maxFehlversuche) {
                JOptionPane.showMessageDialog(this, "Spiel vorbei! Das Wort war: " + aktuellesWort);
                neuesSpielStarten();
            }
        } else if (eingabe.length() > 1) {
            // Ganzes Wort
            if (eingabe.equals(aktuellesWort)) {
                JOptionPane.showMessageDialog(this, "Glückwunsch! Du hast das Wort erraten.");
                neuesSpielStarten();
            } else {
                fehlversuche++;
                fehlversucheLabel.setText("Fehlversuche: " + fehlversuche + " / " + maxFehlversuche);
                bildAktualisieren(fehlversuche);
                falscheBuchstabenLabel.setText("Falsche Buchstaben: " + falscheBuchstaben);

                if (fehlversuche >= maxFehlversuche) {
                    JOptionPane.showMessageDialog(this, "Spiel vorbei! Das Wort war: " + aktuellesWort);
                    neuesSpielStarten();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie einen gültigen Buchstaben oder ein Wort ein.");
        }
    }

    // Diese Methode überprüft, ob der Benutzer ein neues Spiel starten möchte
    private void neuesSpielStarten() {
        int auswahl = JOptionPane.showConfirmDialog(this, "Möchtest du ein neues Spiel starten?", "Neues Spiel", JOptionPane.YES_NO_OPTION);
        if (auswahl == JOptionPane.YES_OPTION) {
            // Spiel zurücksetzen und neu starten
            resetSpiel();
        } else {
            // Spiel beenden
            System.exit(0);
        }
    }

    // Methode zum Zurücksetzen des Spiels
    private void resetSpiel() {
        fehlversuche = 0;
        aktuellesWort = zufaelligesWort();  // Wähle ein neues zufälliges Wort
        angezeigtesWort = new StringBuilder("_".repeat(aktuellesWort.length()));  // Zurücksetzen der Wortanzeige
        wortLabel.setText(printWordWithSpaces(angezeigtesWort));
        fehlversucheLabel.setText("Fehlversuche: 0 / " + maxFehlversuche);
        bildAktualisieren(fehlversuche);
        falscheBuchstaben.clear();
        falscheBuchstabenLabel.setText("Falsche Buchstaben: ");
    }

    // Methode zur Auswahl eines zufälligen Wortes
    private String zufaelligesWort() {
        Random rand = new Random();
        return wortListe.get(rand.nextInt(wortListe.size()));
    }

    // Methode zum Ändern der Anzahl der Versuche
    private void versucheAendern() {
        String neueVersuche = JOptionPane.showInputDialog(this, "Geben Sie die neue Anzahl der Versuche ein:", maxFehlversuche);
        if (neueVersuche != null) {
            try {
                maxFehlversuche = Integer.parseInt(neueVersuche);
                fehlversucheLabel.setText("Fehlversuche: " + fehlversuche + " / " + maxFehlversuche);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Bitte geben Sie eine gültige Zahl ein.");
            }
        }
    }

    private String printWordWithSpaces(StringBuilder wort) {
        return wort.toString().replace("", " ").trim();
    }

    public static void main(String[] args) {
        new HangmanGUI();
    }
}