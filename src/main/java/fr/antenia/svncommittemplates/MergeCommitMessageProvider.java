package fr.antenia.svncommittemplates;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.changes.LocalChangeList;
import com.intellij.openapi.vcs.changes.ui.CommitMessageProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MergeCommitMessageProvider implements CommitMessageProvider {

	@Override
	public @Nullable String getCommitMessage(@NotNull LocalChangeList forChangelist, @NotNull Project project) {
		String comment = forChangelist.getComment();

		Pattern mergePattern = Pattern.compile("^merged from .*\\R((?:.|\\R+)*)\\s?\\[from revision ((?:\\d+,?)+)](?:\\R+)?$", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

		if (comment != null) {
			Matcher matcher = mergePattern.matcher(comment);

			if (matcher.matches()) {
				String revNumber = matcher.group(2).replaceAll(",", "");
				return "[MERGE] r" + revNumber + "\n" + comment.replaceAll("^.+\\R", "").replaceAll("\\s\\[from revision.+$", "");
			}
		}

		return comment;
	}

}
