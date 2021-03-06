package com.github.agadar.telegrammer.client;

import com.github.agadar.telegrammer.core.manager.HistoryManager;
import com.github.agadar.telegrammer.core.manager.PropertiesManager;
import com.github.agadar.telegrammer.core.manager.TelegramManager;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main entry for this application.
 *
 * @author Agadar (https://github.com/Agadar/)n
 */
public class Main {

    public static void main(String args[]) {
        // Retrieve properties and history.
        PropertiesManager.get().loadProperties();
        HistoryManager.get().loadHistory();

        // Set-up graphical form.      
        try {
            // Set cross-platform look&feel.
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

            // Create and display the form.
            java.awt.EventQueue.invokeLater(()
                    -> {
                final NSTelegramForm form = new NSTelegramForm();
                TelegramManager.get().addListeners(form);   // subscribe form to TelegramManager.
                form.setLocationRelativeTo(null);
                form.setVisible(true);
            });
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException ex) {
            Logger.getLogger(NSTelegramForm.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
}
