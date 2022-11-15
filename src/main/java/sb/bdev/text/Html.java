package sb.bdev.text;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Html {

    private final String source;

    public String stripTags() {
        return source.replaceAll("<br/?>", "\n")
                .replaceAll("</?p>", "\n")
                .replaceAll("<[^>]*+>", "")
                .replace("&nbsp;", " ");
    }

    public String format() {
        return String.format(
                "<html>%s",
                source.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;")
                        .replace("\r?\n", "<br/>")
        );
    }
}
