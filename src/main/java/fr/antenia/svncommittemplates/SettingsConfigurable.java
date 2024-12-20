package fr.antenia.svncommittemplates;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.ListCellRendererWrapper;
import com.intellij.ui.components.JBList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SettingsConfigurable implements Configurable {

	private List<Template> templates = new ArrayList<>();
	private JBList<Template> templateList;
	private JTextArea templateEditor;

	@Override
	public String getDisplayName() {
		return "SVN Commit Message Templates";
	}

	@Override
	public JComponent createComponent() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// List of templates
		templateList = new JBList<>();
		templateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		templateList.setCellRenderer(new ListCellRendererWrapper<Template>() {
			@Override
			public void customize(JList list, Template value, int index, boolean selected, boolean hasFocus) {
				this.setText(value.getName());
			}
		});
		templateList.setListData(templates.toArray(new Template[0]));
		templateList.addListSelectionListener(e -> this.onTemplateSelected());

		// Template editor
		templateEditor = new JTextArea(10, 30);
		templateEditor.setEnabled(false);
		templateEditor.setWrapStyleWord(true);
		templateEditor.setLineWrap(true);
		templateEditor.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				SettingsConfigurable.this.saveEditorContentToTemplate();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				SettingsConfigurable.this.saveEditorContentToTemplate();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				SettingsConfigurable.this.saveEditorContentToTemplate();
			}
		});

		// Add/Remove buttons
		JButton addButton = new JButton("Add Template");
		addButton.addActionListener(e -> this.onAddTemplate());

		JButton removeButton = new JButton("Remove Template");
		removeButton.addActionListener(e -> this.onRemoveTemplate());

		JButton resetButton = new JButton("Reset Templates");
		resetButton.addActionListener(e -> this.reset(Utils.copyTemplates(Statics.DEFAULT_TEMPLATES)));

		// Layout
		panel.add(new JScrollPane(templateList), BorderLayout.WEST);
		JPanel editorPanel = new JPanel();
		editorPanel.setLayout(new BorderLayout());
		editorPanel.add(new JScrollPane(templateEditor), BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(removeButton);
		buttonPanel.add(resetButton);
		editorPanel.add(buttonPanel, BorderLayout.SOUTH);
		panel.add(editorPanel, BorderLayout.CENTER);

		return panel;
	}

	@Override
	public boolean isModified() {
		List<Template> savedTemplates = Utils.copyTemplates(Settings.getInstance().getTemplates());
		return !savedTemplates.equals(templates);
	}

	@Override
	public void apply() {
		Settings.getInstance().setTemplates(Utils.copyTemplates(this.templates));
	}

	@Override
	public void reset() {
		this.reset(Utils.copyTemplates(Settings.getInstance().getTemplates()));
	}

	public void reset(List<Template> newTemplates) {
		templates.clear();
		templates.addAll(newTemplates);

		templateList.setListData(templates.toArray(new Template[0]));

		if (!templates.isEmpty()) {
			Template firstTemplate = templates.get(0);
			templateList.setSelectedValue(firstTemplate, true);
			templateEditor.setText(firstTemplate.getContent());
		} else {
			templateEditor.setText(""); // No templates, clear the editor
		}
	}

	private void onTemplateSelected() {
		Template selectedTemplate = templateList.getSelectedValue();
		if (selectedTemplate != null) {
			templateEditor.setEnabled(true); // Enable the editor when a template is selected
			templateEditor.setText(selectedTemplate.getContent()); // Populate the editor with the selected template content
		} else {
			templateEditor.setEnabled(false); // Disable the editor when no template is selected
			templateEditor.setText(""); // Clear the editor if no template is selected
		}
	}

	private void onAddTemplate() {
		String templateName = Messages.showInputDialog("Enter template name:", "New Template", Messages.getQuestionIcon());
		if (templateName != null && !templateName.isEmpty()) {
			templates.add(new Template(templateName, ""));
			templateList.setListData(templates.toArray(new Template[0]));
		}
	}

	private void onRemoveTemplate() {
		Template selectedTemplate = templateList.getSelectedValue();
		if (selectedTemplate != null) {
			templates.remove(selectedTemplate);
			templateList.setListData(templates.toArray(new Template[0]));
		}
	}

	private void saveEditorContentToTemplate() {
		Template selectedTemplate = templateList.getSelectedValue();
		if (selectedTemplate != null) {
			selectedTemplate.setContent(templateEditor.getText());
		}
	}

}