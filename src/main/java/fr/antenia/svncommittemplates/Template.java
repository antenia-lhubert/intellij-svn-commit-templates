package fr.antenia.svncommittemplates;

import java.util.Objects;

public class Template{
	private String name;
	private String content;
	
	public Template() {}
	
	public Template(String name, String content) {
		this.name = name;
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Template copy() {
		Template newTemplate = new Template();
		newTemplate.setName(this.getName());
		newTemplate.setContent(this.getContent());
		return newTemplate;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || this.getClass() != o.getClass()) return false;
		Template template = (Template) o;
		return Objects.equals(name, template.name) && Objects.equals(content, template.content);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, content);
	}
}
