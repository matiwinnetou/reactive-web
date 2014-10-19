package ui;

import play.twirl.api.Format;
import play.twirl.api.HtmlFormat;
import scala.collection.JavaConversions;
import scala.collection.immutable.Seq;

import java.util.List;

/**
 * Created by Mateusz Szczap on 19/10/14.
 */
public class StreamFormat implements Format<HtmlStream> {

    @Override
    public HtmlStream raw(String text) {
        return HtmlStream.apply(text);
    }

    @Override
    public HtmlStream escape(String text) {
        return raw(HtmlFormat.escape(text).body());
    }

    @Override
    public HtmlStream empty() {
        return raw("");
    }

    @Override
    public HtmlStream fill(Seq<HtmlStream> elements) {
        final List<HtmlStream> htmlStreamList = JavaConversions.bufferAsJavaList(elements.toBuffer());

        return htmlStreamList.stream().reduce(HtmlStream.empty(), (a, b) -> a.andThen(b));
    }

    public static final StreamFormat instance = new StreamFormat();

}
