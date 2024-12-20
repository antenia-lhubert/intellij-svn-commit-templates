package fr.antenia.svncommittemplates;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vcs.CommitMessageI;
import com.intellij.openapi.vcs.ui.Refreshable;
import org.jetbrains.annotations.NotNull;

public class UseCommitTemplateAction extends AnAction {

	private final Template template;

	public UseCommitTemplateAction(Template template) {
		super(template.getName());
		this.template = template;
	}

	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		Refreshable data = Refreshable.PANEL_KEY.getData(e.getDataContext());
		if (data instanceof CommitMessageI commitMessageI) {
			commitMessageI.setCommitMessage(this.template.getContent());
		}
	}
}
