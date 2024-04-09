const desc = $('#descriptionRe');
const textWithBrTags = desc.text();
const textWithLineBreaks = textWithBrTags.replace(/<br\/>/g, "\n");
desc.html(textWithLineBreaks)
