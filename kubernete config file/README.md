first part:

jar file using  do->"mvc clean package -DSkipTests"

minikube (need to run it "make sure is start")

THEN:

-> For each service directory run "& minikube -p minikube docker-env --shell powershell | Invoke-Expression" on powershell

-> Build docker file in minikube docker -> "docker build --tag=<service-name>:<tag> ."

After these:

-> go to kubernate config file and run "kubectl apply -f ./" on powershell

need download kube-forward app from "https://github.com/pixel-point/kube-forwarder"
