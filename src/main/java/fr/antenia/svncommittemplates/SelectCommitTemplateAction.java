package fr.antenia.svncommittemplates;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SelectCommitTemplateAction extends AnAction {

	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		DefaultActionGroup actionGroup = new DefaultActionGroup();

		Settings.getInstance().getTemplates().forEach(template -> actionGroup.add(new UseCommitTemplateAction(template)));

		JBPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
				"Select Template",
				actionGroup,
				e.getDataContext(),
				JBPopupFactory.ActionSelectionAid.MNEMONICS,
				false
		);
		this.showPopupUnderButton(e, popup);
	}

	private void showPopupUnderButton(AnActionEvent e, JBPopup popup) {
		JComponent button = (JComponent) Objects.requireNonNull(e.getInputEvent()).getSource();

		Point buttonLocation = button.getLocationOnScreen();
		Dimension buttonSize = button.getSize();

		int x = buttonLocation.x;
		int y = buttonLocation.y + buttonSize.height;

		popup.setLocation(new Point(x, y));
		popup.showInBestPositionFor(e.getDataContext());
	}
}
