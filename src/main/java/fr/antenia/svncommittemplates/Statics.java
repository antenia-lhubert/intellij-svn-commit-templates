package fr.antenia.svncommittemplates;

import java.util.Arrays;
import java.util.List;

public class Statics {
	public static final List<Template> DEFAULT_TEMPLATES = Arrays.asList(new Template("evo", "[EVO] - Mantis : xxxxx : TITRE\n\n> "), new Template("bug", "[BUG] - Mantis : xxxxx : TITRE\n\n> "), new Template("bug transversal", "[BUG_TRANSVERSAL] - Mantis : xxxxx : TITRE\n\n> "), new Template("struct", "[STRUCT] - Mantis : xxxxx : TITRE\n\n> "), new Template("code review", "[CODE_REVIEW] - TITRE\n\n> "));
}
