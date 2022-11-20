package parallel;

import java.util.concurrent.BlockingQueue;

public interface FProductor<E> {
    public default Productor<E> create(BlockingQueue<E> blockingQueue){
        return new Productor<E>(blockingQueue) {
            @Override
            public void product(BlockingQueue<E> blockingQueue) {
                generate(blockingQueue);
            }
        };
    }
    public default Productor<E> create(){
        return new Productor<E>() {
            @Override
            public void product(BlockingQueue<E> blockingQueue) {
                generate(blockingQueue);
            }
        };
    }

    public void generate(BlockingQueue<E> blockingQueue);
}
