package fr.antenia.svncommittemplates;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.annotations.XCollection;

import java.util.List;

@State(
		name = "Settings",
		storages = @Storage("SvnCommitTemplatesSettings.xml")
)
public class Settings implements PersistentStateComponent<Settings> {

	@XCollection
	private List<Template> templates = Utils.copyTemplates(Statics.DEFAULT_TEMPLATES);

	public List<Template> getTemplates() {
		return templates;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

	@Override
	public Settings getState() {
		return this;
	}

	@Override
	public void loadState(Settings state) {
		if (state != null) {
			this.templates = state.getTemplates();
		}
	}

	public static Settings getInstance() {
		return ServiceManager.getService(Settings.class);
	}
}
