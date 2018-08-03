import asserts.ImprovedAssertions;
import asserts.ImprovedSoftAssertions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        int number = 10;
        ExecutorService exec = Executors.newFixedThreadPool(number);
        List<Callable<Void>> tasks = new ArrayList<>();
        ScoreQuestion question = new ScoreQuestion();
        question.setType("type");
        question.setText("question text");
        ImprovedAssertions.assertThatResultOf(question::getText).isEqualTo("1");
        for (int i = 0; i < number; i++) {
            int dev3 = i % 3;
            if (dev3 == 0) {
                tasks.add(() -> {
                    ImprovedSoftAssertions.assertSoftly(soft->
                       soft.assertThatResultOf(question::getType).isEqualTo(""));

                    return null;
                });
            } else if (dev3 == 1) {
                tasks.add(() -> { ImprovedSoftAssertions.assertSoftly(soft->
                        soft.assertThatResultOf(question::getMinScore).isEqualTo(10));
                    return null;
                });
            } else  {
                tasks.add(() -> {
                    ImprovedAssertions.assertThatResultOf(question::getText).isEqualTo("");
                    return null;
                });
            }
        }
        try {
            for (Future f : exec.invokeAll(tasks)) {
                try {
                    f.get();
                } catch (ExecutionException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            exec.shutdownNow();
        }

    }
}
