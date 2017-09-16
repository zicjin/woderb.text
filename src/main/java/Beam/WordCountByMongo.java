package Beam;

import Beam.common.ExampleUtils;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.mongodb.MongoDbIO;
import org.apache.beam.sdk.metrics.Counter;
import org.apache.beam.sdk.metrics.Metrics;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.bson.Document;

public class WordCountByMongo {

    static class ExtractWordsFn extends DoFn<Document, String> {
        private final Counter emptyLines = Metrics.counter(WordCount.ExtractWordsFn.class, "emptyLines");

        @ProcessElement
        public void processElement(ProcessContext c) {
            String title = c.element().get("title").toString();
            if (title.isEmpty()) {
                emptyLines.inc();
            }

            // Split the line into words.
            String[] words = title.split(ExampleUtils.TOKENIZER_PATTERN);

            // Output each word encountered into the output PCollection.
            for (String word : words) {
                if (!word.isEmpty()) {
                    c.output(word);
                }
            }
        }
    }

    public static class CountWords extends PTransform<PCollection<Document>, PCollection<KV<String, Long>>> {
        @Override
        public PCollection<KV<String, Long>> expand(PCollection<Document> lines) {

            // Convert lines of text into individual words.
            PCollection<String> words = lines.apply(ParDo.of(new ExtractWordsFn()));

            // Count the number of times each word occurs.
            return words.apply(Count.<String>perElement());
        }
    }

    public static class AddToDocument extends SimpleFunction<KV<String, Long>, Document> {
        @Override
        public Document apply(KV<String, Long> input) {
            Document doc = new Document("word", input.getKey());
            doc.put("count", input.getValue());
            return doc;
        }
    }

    public static void main(String[] args) {
//        System.out.print("args.length:" + args.length + args[0]);

        PipelineOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(PipelineOptions.class);
        Pipeline p = Pipeline.create(options);

        p.apply(MongoDbIO.read()
                .withUri("mongodb://zicjin_dev:z5@mongo.woderb.com:27017")
                .withDatabase("spiderman_dev")
                .withCollection("Articles"))
        .apply("CountWords", new CountWords())
        .apply("Format", MapElements.via(new AddToDocument()))
        .apply(MongoDbIO.write()
                .withUri("mongodb://zicjin_dev:z5@mongo.woderb.com:27017")
                .withDatabase("spiderman_dev")
                .withCollection("ArticlesBeam"));

        p.run().waitUntilFinish();
    }
}

