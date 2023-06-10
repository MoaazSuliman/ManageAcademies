package Service;

import Model.StudentCourse;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.print.PrinterException;

public class PrintRecietService {

    private StudentCourse studentCourse;
    private SimpleAttributeSet small = new SimpleAttributeSet();
    private SimpleAttributeSet large = new SimpleAttributeSet();
    private StyledDocument document;
    private JTextPane view = new JTextPane();

    public PrintRecietService(StudentCourse studentCourse) {
        this.studentCourse = studentCourse;
        setStyles();
        setDefaultForView();
        setStudentCourseValuesInView();
    }


    private void setDefaultForView() {
        try {
            view.setFont(new Font("Tahoma", Font.BOLD, 15));
            document = view.getStyledDocument();
            document.insertString(0, "\n\t\t  &$Reciet$&", large);
            document.insertString(document.getLength(), "\n\t\t      &$DCU$&", small);
            document.insertString(document.getLength(), "\n\t      &$ " + studentCourse.getDate() + " $&", large);
        } catch (BadLocationException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    private void setStyles() {
        StyleConstants.setFontSize(small, 18);
        StyleConstants.setFontSize(large, 24);
    }

    private void addLine() {
        try {
            document.insertString(document.getLength(), "-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-#-", small);
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void setStudentCourseValuesInView() {

        try {
            document.insertString(document.getLength(), "\n\n Student Name : \n", large);
            document.insertString(document.getLength(), "\t" + studentCourse.getStudent().getName() + "\n", small);
            addLine();

            document.insertString(document.getLength(), "\n Course Name : ", large);
            document.insertString(document.getLength(), studentCourse.getCourse().getName(), small);
            if (studentCourse.getLevel().getId() >= 1) {
                document.insertString(document.getLength(), "\n Level Name : ", small);
                document.insertString(document.getLength(), studentCourse.getLevel().getName(), small);
            } else {
                document.insertString(document.getLength(), "\n Level Name : ", small);
                document.insertString(document.getLength(), "All Course ", small);
            }
            if (studentCourse.getMonths() >= 1) {
                document.insertString(document.getLength(), "\n Months : ", small);
                document.insertString(document.getLength(), String.valueOf(studentCourse.getMonths()), small);

            }
            document.insertString(document.getLength(), "\n Course Money : ", small);
            document.insertString(document.getLength(), String.valueOf(studentCourse.getCourseMoney()), small);

            document.insertString(document.getLength(), "\n Payed Money : ", small);
            document.insertString(document.getLength(), String.valueOf(studentCourse.getPayedMoney()), small);

            document.insertString(document.getLength(), "\n Remaining Money : ", small);
            document.insertString(document.getLength(), String.valueOf(getRemainingBalance()), small);

//            document.insertString(document.getLength(), "\n Date  : ", small);
//            document.insertString(document.getLength(), studentCourse.getDate(), small);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private double getRemainingBalance() {
        return studentCourse.getCourseMoney() - studentCourse.getPayedMoney();
    }

    public void print() {
        try {
            view.print();
        } catch (PrinterException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }


}
