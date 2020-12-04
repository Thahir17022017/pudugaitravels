<div id="admin">
    <nav class="navbar fixed-top navbar-expand-lg scrolling-navbar shadow-card pudubar">
        <a class="navbar-brand" href="#"> 
            <img src="icons/actualogo.png" height="60px" width="150px" alt="base image">
        </a> 
        
        
    </nav>
    
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-4">
            </div>
            <div class="col-sm-4" style="margin-top:200px;">
                <!--Form with header-->
                <div class="card shadow-card" style="border-radius:10px;">
                    <div class="card-block p-3">

                        <!--Header-->
                        <div class="form-header shadow-card" style="padding:20px;background-color:#072F41;border-radius:10px;">
                            <h3 class="loginLabels" style="color:white;"> <i class="fa fa-lock" style="color:white;"></i> &nbsp;Login :</h3>
                            
                        </div>

                        <!--Body-->
                        <form>
                        <div class="md-form">
                            <i class="fa fa-envelope prefix" style="color:#072F41;"></i>
                            <input type="text" style="color:#072F41;" id="form2" class="form-control">
                            <label for="form2" style="color:#072F41;" class="loginLabels">Username :</label>
                        </div>

                        <div class="md-form">
                            <i class="fa fa-lock prefix" style="color:#072F41;"></i>
                            <input type="password" id="form4" class="form-control">
                            <label for="form4" style="color:#072F41;" class="loginLabels">Password :</label>
                        </div>

                        <div class="text-center">
                            <button type="button" class="btn btnHover loginLabels" style="background-color:#072F41;border-radius:30px;width:200px;" onclick="login()">Login</button>
                        </div>
                        
                        <br>
                        <div class="text-center">
                             <span id="errorMsg" style="color:red;visibility:hidden;">Username is not correct</span>
                        </div>
                        <div class="text-center">
                            <div id="progressBar" class="lds-facebook" style="visibility:hidden;"><div></div><div></div><div></div></div>
                        </div>
                        </form>
                        


                    </div>

                   

                </div>
                <!--/Form with header-->
            </div>
            <div class="col-sm-4">

            </div>  
        </div>  
    </div>
   

    <!-- <footer class="page-footer pt-1 footerColor" style="position:relative;">
                
                
        

   
    <div class="footer-copyright py-3 text-center">
        © 2018 - 19 Copyright:
        <a href="#">Pudugai Travels</a> | <a target="_blank" href="https://www.google.com/maps/search/?api=1&query=Fz+Systems+Trichy" style="font-size:14px">Powered by Fz Systems,Trichy</a>
    </div>
  

</footer> -->

</div>