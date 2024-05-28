package robots.game;

import javax.swing.*;

/**
 * Класс для инкапсулирования логики работы модуля с игрой.
 */
public class ModuleController implements MVCControllerWithWindows {
    private final GameVisualizer gameVisualizer;
    private final JInternalFrame[] jInternalFrames;

    /**
     * Конструктор модуля с какой-либо базовой логикой робота
     */
    public ModuleController() {
        RobotModel robotModel = new RobotModel();
        gameVisualizer = new GameVisualizer(robotModel);
        GameController gameController = new GameController(robotModel, gameVisualizer);

        WindowWithCoordinates wwc = new WindowWithCoordinates();
        robotModel.addListener(wwc);
        jInternalFrames = new JInternalFrame[]{wwc};
        
        gameController.start();
    }

    /**
     * Предоставление отрисовщика робота
     * @return графический элемент, который будет отрисовывать работу робота
     */
    @Override
    public JComponent getPainter() {
        return gameVisualizer;
    }

    /**
     * Возвращает список зависимых окон
     * @return массив с зависимыми окнами
     */
    @Override
    public JInternalFrame[] getWindows() {
        return jInternalFrames;
    }
}
