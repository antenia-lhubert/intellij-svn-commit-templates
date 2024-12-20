package fr.antenia.svncommittemplates;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {
	
	public static List<Template> copyTemplates(List<Template> templates) {
		return templates.stream().map(Template::copy).collect(Collectors.toList());
	}
	
}
