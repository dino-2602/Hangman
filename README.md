# Hangman

<a name="readme-top"></a>
**Author:** Dino Haskic  
LBS Eibiswald | 2aAPC  
Erstellt am: 13.10.2024

Diese Anwendung ist die siebte Übung vom Labor ITL12, bei der es darum ging, ein voll funktionsfähiges Hangman-Spiel in Java zu erstellen.

## Das Ziel der Übung
### • Erstellung eines Hangman-Algorithmus
### • Planung der Klassenstruktur
### • Umsetzung in Java mit der Swing-Klasse (GUI)

**Teil 1:**
- Welche Klassen werden benötigt?
- Wie erfolgt die Ausgabe des Status?
- Zählung der Fehlversuche von 0 bis 9 ("Game Over" danach).
- Eingabe der Buchstaben über ein Textfeld.
- Ausgabe des Lösungswortes mit „_“ für versteckte Buchstaben.
- History der bereits eingegebenen Buchstaben anzeigen.
- Neustart des Spiels, wenn das Wort gelöst wurde.
- Wortliste: statisch im Array.
- Grafische Ausgabe mit 9 Bildern – pro Fehlversuch wird ein Bild im Panel geladen.

**Teil 2:**
- Einstellbare Anzahl der Fehlversuche (JMenu).
- Dynamisches Lesen der Wörter aus einer Datei (mittels File & Scanner Klasse).
- History der Buchstaben als Option im Menü (JCheckBoxMenuItem).

## Umsetzung
Im ersten Teil wurde ein Hangman-Bild von Flaticon heruntergeladen und mit Windows Paint bearbeitet, um es an das Spiel anzupassen. Es gibt für jede Stufe des Spiels ein eigenes Bild, das die Entwicklung des Hangmans darstellt. Diese Bilder sind einfach gehalten, um die visuelle Entwicklung des Spiels klar und ansprechend zu präsentieren.

In der aktuellen Version wurde ein Einstellungsmenü integriert, mit dem der Benutzer die Anzahl der Fehlversuche anpassen kann. Dies geschieht über eine neue Menüleiste, die mit der Klasse `JMenuBar` erstellt wurde.

Das "Einstellungen"-Menü ermöglicht es, die maximale Anzahl an Fehlversuchen festzulegen, wodurch der Schwierigkeitsgrad des Spiels angepasst werden kann.

Zusammengefasst bietet diese Version des Spiels:
- Ein Menü zur Anpassung der Einstellungen.
- Eine neue Option zur Eingabe der maximalen Fehlversuche.

## Installation
```cmd
git clone https://github.com/dino-2602/Hangman
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Verwendung
Technologien im Einsatz:
[![Java][java.com]][java-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>
Verbindung zur Datenbank (optional)
Falls die Anwendung auch Datenbank-Funktionalität benötigt, kann die Verbindung zur Datenbank mit Main.java erfolgen, wobei die Klassen Connection, DriverManager, ResultSet, SQLException und Statement genutzt werden. Dies ist jedoch optional für die Basisfunktion des Hangman-Spiels.

<p align="right">(<a href="#readme-top">back to top</a>)</p> <!-- MARKDOWN LINKS & IMAGES --> <!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

