/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter.renderers;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import moritz.schmoetzer.currencyconverter.ConverterModel;

/**
 *
 * @author Moritz Schmötzer
 */
public class CurrencyRenderer implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = new JLabel();
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] text = ((String) value).split(";"); // Alters the appereance of the ComboBox

        HashMap<String, ImageIcon> countries = ConverterModel.loadCountries();
        label.setIcon(countries.get(text[2])); // Sets the country flag of the currency as the icon
        label.setToolTipText(text[0] + " - " + text[1]); // Sets a description of the flag
        return label;
    }
}
