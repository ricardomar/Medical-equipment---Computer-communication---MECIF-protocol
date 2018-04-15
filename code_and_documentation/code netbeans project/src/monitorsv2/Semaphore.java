package monitorsv2;

class Semaphore {
    private int init;

    public Semaphore(int init) {
        this.init = init;
    }

    synchronized void espera() {
        while (init == 0)
            try {
                wait();
            } catch (InterruptedException e) {
            }
        init = 0;
    }

    synchronized void notifica() {
        init = 1;
        notify();
    }
}
