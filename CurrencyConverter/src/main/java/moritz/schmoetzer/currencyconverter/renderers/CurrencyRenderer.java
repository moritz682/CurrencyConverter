/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter.renderers;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Moritz Schmötzer
 */
public class CurrencyRenderer implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = new JLabel();

        String[] text = ((String) value).split(";"); // Alters the appereance of the ComboBox
        label.setText(text[0] + " - " + text[1]);

        return label;
    }
}
