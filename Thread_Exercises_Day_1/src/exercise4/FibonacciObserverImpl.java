package exercise4;

public class FibonacciObserverImpl implements FibonacciObserver
{

    private javax.swing.JTextField jTextField5;
    
    public FibonacciObserverImpl(javax.swing.JTextField jTextField5)
    {
        this.jTextField5 = jTextField5;
    }
    
    @Override
    public void dataReady(long tal)
    {
        jTextField5.setText("" + tal);
    }

}
