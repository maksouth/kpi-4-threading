package lab2;

import lab2.domain.impl.analytic.AnalyticProcessDispatcher;

public interface View {
    void showAnalytics(AnalyticProcessDispatcher.Analytics analytics);
}
