import com.checkout.CheckOut;
import com.build.MVNBuild;

def checkOut = new CheckOut(this)
def mvnBuild = new MVNBuild(this)
def call() {
  echo "Start..."
}
