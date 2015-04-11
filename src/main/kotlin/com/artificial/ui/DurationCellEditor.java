package com.artificial.ui;

import com.artificial.util.UtilPackage;
import org.apache.commons.lang3.StringUtils;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Yurii on 4/9/2015.
 */
public class DurationCellEditor extends DefaultCellEditor {
	private static final String CUSTOM_DURATION_PATTERN = "^(?<hours>\\d+):(?<minutes>\\d+)$";
	private static final Pattern PATTERN = Pattern.compile(CUSTOM_DURATION_PATTERN);

	private final JTextField textField;

	public DurationCellEditor() {
		this(new JTextField());
	}

	public DurationCellEditor(JTextField textField) {
		super(textField);
		this.textField = textField;
		textField.removeActionListener(delegate);
		delegate = new EditorDelegate() {
			public void setValue(Object value) {
				final String text;
				if (value instanceof Duration) {
					Duration duration = (Duration) value;
					text = UtilPackage.getHoursMinutes(duration);
				} else {
					text = "";
				}
				textField.setText(text);
			}

			public Object getCellEditorValue() {
				return getCellEditorValue();
			}
		};
		textField.addActionListener(delegate);
	}

	@Override public Object getCellEditorValue() {
		final String text = textField.getText();
		if (StringUtils.isBlank(text)){
			return Duration.ZERO;
		} else if (StringUtils.isNumeric(text)){
			return Duration.ofMinutes(Long.parseLong(text));
		}
		final Matcher matcher = PATTERN.matcher(text);
		if (matcher.matches()){
			final int minutes = Integer.parseInt(matcher.group("minutes"));
			final int hours = Integer.parseInt(matcher.group("hours"));
			return Duration.ofMinutes(hours * 60 + minutes);
		}
		try {
			return Duration.parse(text);
		} catch (DateTimeParseException e) {
			return Duration.ZERO;
		}
	}
}
