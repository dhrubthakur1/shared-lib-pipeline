import com.checkout.CheckOut;
import com.build.MVNBuild;

def checkOut = new CheckOut(this)
def mvnBuild = new MVNBuild(this)

def call(String name = 'User') {
 echo "Welcome shared, ${name}."
}

def testShared(String name = 'User') {
 echo "Test, ${name}."
}
