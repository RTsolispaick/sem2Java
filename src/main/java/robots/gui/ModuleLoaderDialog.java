package robots.gui;

import robots.locale.LanguageManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * Диалог для загрузки модуля из JAR файла.
 * Предоставляет возможность выбрать JAR файл и указать путь к классу модуля внутри JAR.
 */
public class ModuleLoaderDialog extends JDialog {
    private final JTextField jarFilePathField;
    private final JTextField moduleClassPathField;
    private final JTextArea statusTextArea;
    private File selectedJarFile;

    /**
     * Конструктор для создания диалога загрузчика модуля.
     *
     * @param parent родительский фрейм для модального диалога.
     */
    public ModuleLoaderDialog(Frame parent) {
        super(parent, LanguageManager.getStr("PaneMenuBar.optionMenu.dialogLoader"), true);
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        jarFilePathField = new JTextField(30);
        jarFilePathField.setEditable(false);
        moduleClassPathField = new JTextField(30);
        JButton browseButton = new JButton(LanguageManager.getStr("PaneMenuBar.optionMenu.browse"));
        JButton loadButton = new JButton(LanguageManager.getStr("PaneMenuBar.optionMenu.load"));
        statusTextArea = new JTextArea();
        statusTextArea.setEditable(false);

        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel(LanguageManager.getStr("moduleLoaderDialog.fileChooser.title")), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(jarFilePathField, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(browseButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(new JLabel(LanguageManager.getStr("moduleLoaderDialog.modulePath.title")), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(moduleClassPathField, gbc);

        add(inputPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(statusTextArea);
        add(scrollPane, BorderLayout.CENTER);

        add(loadButton, BorderLayout.SOUTH);

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Jar", "jar"));
            int result = fileChooser.showOpenDialog(ModuleLoaderDialog.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedJarFile = fileChooser.getSelectedFile();
                jarFilePathField.setText(selectedJarFile.getAbsolutePath());
            }
        });

        loadButton.addActionListener(e -> {
            String jarPath = jarFilePathField.getText();
            String modulePath = moduleClassPathField.getText();
            if (jarPath.isEmpty() || modulePath.isEmpty()) {
                statusTextArea.append(LanguageManager.getStr("moduleLoaderDialog.notEmptyField"));
                return;
            }

            dispose();
        });

        pack();
        setVisible(true);
    }

    /**
     * Возвращает выбранный JAR файл.
     *
     * @return выбранный JAR файл.
     */
    public File getSelectedJarFile() {
        return selectedJarFile;
    }

    /**
     * Возвращает путь к классу модуля внутри JAR.
     *
     * @return путь к классу модуля.
     */
    public String getModuleClassPath() {
        return moduleClassPathField.getText();
    }
}
