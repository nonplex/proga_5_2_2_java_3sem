// Пакет, в котором находится основной класс программы
package main.java;

// Импорт необходимых библиотек
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.*;

// Основной класс программы
public class Main extends JPanel
{
    // BufferedImage для хранения фонового изображения
    BufferedImage background_image;

    // Цвета для заливки, контура и анимированной части
    Color fill;
    Color border;
    Color animated_part;

    // Флаг для отслеживания состояния огня (открыт/закрыт)
    boolean draw_ogon;

    // Кнопка для запуска анимации
    JButton start_animate;

    // Таймер для анимации
    javax.swing.Timer animation_timer;

    // Задержка между кадрами анимации
    int animation_delay;

    // Счетчик кадров анимации
    int animation_counter;

    // Текстовое поле для ввода задержки между кадрами анимации
    JTextField animation_delay_textfield;

    // Кнопка для смены цвета
    JButton choose_color;

    // Объект JColorChooser для выбора цвета
    JColorChooser color_chooser;

    // Конструктор класса Main
    Main()
    {
        super();
        this.setLayout(null);

        try
        {
            // Загрузка фонового изображения
            background_image = ImageIO.read(new File("C:\\Users\\Vladislav\\Desktop\\3 sem\\proga v21 semestr 3\\5 lab\\qt4.jpeg"));
        }
        catch (IOException ex)
        {
            System.err.println("Файл не найден!");
        }

        // Создание кнопки для смены цвета и добавление слушателя событий
        choose_color = new JButton("Сменить цвет");
        choose_color.setBounds(10, 10, 150, 25);
        choose_color.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // Вызов метода для выбора цвета
                colorsChoose();
                // Перерисовка панели
                repaint();
            }
        });
        add(choose_color);

        // Вызов метода для выбора начальных цветов
        colorsChoose();

        // Установка начальной задержки между кадрами анимации
        animation_delay = 500;

        // Создание текстового поля для ввода задержки между кадрами анимации
        animation_delay_textfield = new JTextField(Integer.toString(animation_delay));
        animation_delay_textfield.setToolTipText("Скорость анимации");
        animation_delay_textfield.setBounds(170, 40, 50, 25);
        add(animation_delay_textfield);

        // Установка начального состояния огня (открыт)
        draw_ogon = true;

        // Инициализация счетчика кадров анимации и таймера
        animation_counter = 0;
        animation_timer = new javax.swing.Timer(animation_delay, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // Перерисовка панели при каждом тике таймера
                repaint();
                animation_counter++;
                // Остановка таймера после 6-го кадра анимации
                if (animation_counter == 6)
                {
                    animation_timer.stop();
                }
            }
        });

        // Создание кнопки для запуска анимации и добавление слушателя событий
        start_animate = new JButton("Начать анимацию");
        start_animate.setBounds(10, 40, 150, 25);
        start_animate.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    // Получение значения задержки между кадрами анимации из текстового поля
                    int temp = animation_delay;
                    animation_delay = Integer.parseInt(animation_delay_textfield.getText());
                    // Проверка на валидность значения задержки
                    if (animation_delay <= 0)
                    {
                        animation_delay = temp;
                        throw new Exception("Ошибка!");
                    }
                }
                catch(Exception ex)
                {
                    // Отображение сообщения об ошибке, если значение задержки невалидно
                    JOptionPane.showMessageDialog(null,
                            "Недопустимое значение\n" + ex.getMessage() ,
                            "Ошибка значения", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                // Сброс счетчика кадров анимации и установка новой задержки между кадрами
                animation_counter = 0;
                animation_timer.setDelay(animation_delay);
                // Запуск анимации
                animation_timer.start();
            }
        });
        add(start_animate);

        // Установка видимости панели
        setVisible(true);
    }

    // Метод для выбора цветов заливки, контура и анимированной части
    private void colorsChoose()
    {
        // Вызов диалога выбора цвета для контура
        border = color_chooser.showDialog(this, "Выберите цвет контура", Color.BLACK);
        // Вызов диалога выбора цвета для заливки
        fill = color_chooser.showDialog(this, "Выберите цвет ракеты", Color.BLACK);
        // Вызов диалога выбора цвета для анимированной части
        animated_part = color_chooser.showDialog(this, "ВЫберите цвет огня", Color.BLACK);
    }

    // Метод для отрисовки компонентов на панели
    public void paintComponent(Graphics gr)
    {
        // Получение объекта Graphics2D для работы с графикой
        Graphics2D g = (Graphics2D) gr;
        // Отрисовка фонового изображения
        g.drawImage(background_image, 0, 0, getWidth(), getHeight(), null);
        // Установка цвета заливки
        g.setColor(fill);
        // Установка ширины контура
        g.setStroke(new BasicStroke(4));

        //Овал ракеты
        g.fillOval(300, 300, 250, 100); // Заливка овала цветом fill
        g.drawOval(300, 300, 250, 100);

        //треугольники ракеты
        // Верхний треугольник
        int[] upperTriangleX = {400, 450, 300 + 125};
        int[] upperTriangleY = {300 - 50, 300 - 50, 300};
        g.fillPolygon(upperTriangleX, upperTriangleY, 3);
        g.drawPolygon(upperTriangleX, upperTriangleY, 3);

        // Нижний треугольник
        int[] lowerTriangleX = {400, 450, 300 + 125};
        int[] lowerTriangleY = {300 + 50 + 100, 300 + 50 + 100, 300 + 100};
        g.fillPolygon(lowerTriangleX, lowerTriangleY, 3);
        g.drawPolygon(lowerTriangleX, lowerTriangleY, 3);


        // Начало рисования илюминатора
        g.setColor(Color.yellow); // Устанавливаем цвет заливки на желтый
        // Создаем объект Ellipse2D с заданными координатами и размерами
        Ellipse2D.Double ilum = new Ellipse2D.Double(425 - 75, 350 - 25, 50, 50);
        // Заполняем эллипс цветом заливки
        g.fill(ilum);
        // Рисуем контур эллипса
        g.draw(ilum);



        // Установка цвета контура
        g.setColor(border);

// Отрисовка контура овала
        g.drawOval(300, 300, 250, 100);

// Отрисовка контура верхнего треугольника
        g.drawPolygon(upperTriangleX, upperTriangleY, 3);

// Отрисовка контура нижнего треугольника
        g.drawPolygon(lowerTriangleX, lowerTriangleY, 3);







//        // Установка цвета контура
        g.setColor(border);


        // Восстановление цвета заливки
        g.setColor(fill);

        if (draw_ogon) {
            // Отрисовка треугольника с контуром
            GeneralPath path = new GeneralPath();
            path.moveTo(560, 300); // правая нижняя точка эллипса
            path.lineTo(500, 350); // вершина треугольника, которая не входит в эллипс
            path.lineTo(560, 400); // вершина треугольника, которая не входит в эллипс
            path.closePath();
            g.setColor(animated_part); // цвет заливки
            g.fill(path); // заливка треугольника
            g.setColor(border); // цвет контура
            g.draw(path); // отрисовка контура

            // Изменение состояния режима на "закрыт"
            draw_ogon = false;
        } else {
            // Отрисовка треугольника с контуром
            GeneralPath path = new GeneralPath();
            path.moveTo(575, 280); // правая нижняя точка эллипса
            path.lineTo(500, 350); // вершина треугольника, которая не входит в эллипс
            path.lineTo(575, 420); // вершина треугольника, которая не входит в эллипс
            path.closePath();
            g.setColor(animated_part); // цвет заливки
            g.fill(path); // заливка треугольника
            g.setColor(border); // цвет контура
            g.draw(path); // отрисовка контура

            // Изменение состояния режима на "открыт"
            draw_ogon = true;
        }
    }

    // Метод main для запуска приложения
    public static void main(String[] args)
    {
        try
        {
            // Создание главного окна приложения
            JFrame main = new JFrame();
            main.setBounds(15, 15, 1000, 700);
            main.setTitle("Ракета");
            main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main.setLayout(null);

            // Создание панели с графическим контентом и добавление ее в главное окно
            Main main_panel = new Main();
            main_panel.setBounds(15, 15, main.getWidth()-45, main.getHeight()-75);
            main.add(main_panel);

            // Установка видимости главного окна
            main.setVisible(true);
        }
        catch (Exception ex)
        {
            // Обработка исключений и завершение работы приложения
            System.err.println("Ошибка!");
            System.err.println(ex.getMessage());
            System.exit(ABORT);
        }
    }
}