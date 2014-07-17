package tests;

import java.util.Date;
import java.io.File;

// Klasse DirListing
public class DirListing {
   public static void main(String[] args) {
      File directory = null;

      // Test, ob Kommandozeilenargument angegeben wurde
      if (args.length == 0) {
         // Falls nicht: Listing des aktuellen
         // Verzeichnisses
         directory = new File("C:");
      } else {
         directory = new File(args[0]);
      }

      // Existiert angegebenes Verzeichnis ?
      if (!directory.exists()) {
         System.out.println("Das Verzeichnis "
                            + "existiert nicht!");
      } else {
         displayListing(directory);
      }
   }

   // Statische Methode, die den Inhalt des Verzeichnisses
   // directory angibt
   private static void displayListing(File directory) {
      // Inhalt von directory
      File[] files = directory.listFiles(); 

      System.out.println("Der Inhalt von "
                         + directory.getName() + " :");
      System.out.println();

      for (int i = 0; i < files.length; i++) {
         File file = files[i];

         // Modifikationsdatum
         System.out.print(new Date(file.lastModified())
                          + "\t");

         // Verzeichnis oder Datei ?
         if (file.isDirectory()) {
            System.out.print("<DIR>\t");
         } else {    
            // Nur die Länge einer Datei interessiert
            System.out.print("\t" + file.length());
         }

         // Name
         System.out.print("\t" + file.getName());
     
         System.out.println();
      }
   }
}