import UIKit
import common

class DependencyManager: NSObject {

    public lazy var uiContext : KotlinCoroutineContext = {
        UI()
    }()
    
    public lazy var logger : PlatformLogger = {
        PlatformLoggerIos()
    }()
    
}