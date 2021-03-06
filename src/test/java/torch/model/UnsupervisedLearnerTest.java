package torch.model;

import torch.RecordComparator;
import torch.comparators.StandardComparators;
import torch.io.FixedWidthFileSchema;

import java.util.Arrays;
import java.util.Random;

import org.junit.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class UnsupervisedLearnerTest {

    private FixedWidthFileSchema schema1, schema2;
    private RecordComparator cmp;
    private UnsupervisedLearner lr;

    @Before
    public void setUp() {
        schema1 = 
            new FixedWidthFileSchema.Builder()
            .column("zip", 1, 5)
            .column("first", 5, 15)
            .column("last", 15, 25)
            .column("age", 25, 25)
            .blockingField("zip")
            .build();

        schema2 = 
            new FixedWidthFileSchema.Builder()
            .column("zip", 1, 5)
            .column("first", 5, 15)
            .column("last", 15, 25)
            .column("age", 25, 25)
            .blockingField("zip")
            .build();

        cmp =
            new RecordComparator.Builder(schema1, schema2)
            .compare("last", StandardComparators.STRING)
            .compare("age", StandardComparators.YEAR)
            .compare("first", StandardComparators.STRING)
            .handleBlanks(false)
            .build();
    }

    @Test
    public void testPartitionOne() {
        Random rng = new Random();
        double[] ary = new double[10];
        UnsupervisedLearner.partitionOne(rng, ary);

        double total = 0.0;
        for (int i = 0; i < ary.length; i++) {
            assertThat(ary[i] >= 0.0, is(true));
            assertThat(ary[i] <= 1.0, is(true));
            total += ary[i];
        }

        assertThat(total, is(1.0));
    }
}
