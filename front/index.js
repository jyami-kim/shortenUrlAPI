
var result

new Vue({
    el: '#form_input',
    data: {
        message: ''
    },
    methods: {
        getDataFromApi(){
            axios.post('http://13.209.168.93:8081/', {
                link_url : this.message
            })
            .then(function(response) {
                console.log(response);
                if(response.data.status == 200){
                    result = "http://13.209.168.93:8081/"+response.data.data

                }else{
                    result = "It is wrong URL"
                }

                console.log(result);
                document.getElementById("result").innerHTML = result
            })
            .catch(function(error) {
                console.log(error)
            });
        }
    }
})

var redirectArray = []
var createArray = []

var count = new Vue({
    data: {

    },
    methods: {
        redirect() {
            axios.get('http://13.209.168.93:8081/count/redirect')
            .then(function(response){
                console.log(response);
                redirectArray = response.data.data;
                console.log(redirectArray);
                var chart1 = new CanvasJS.Chart("chartContainer_redirect", {
                    animationEnabled: true,
                    theme: "light2", // "light1", "light2", "dark1", "dark2"
                    title:{
                        text: "Top5 Redirecting Shorten Url"
                    },
                    axisY: {
                        title: "redirect count"
                    },
                    data: [{        
                        type: "column",  
                        showInLegend: true, 
                        legendMarkerColor: "grey",
                        dataPoints: makeArrayRedirect()
                    }]
                });
                console.log(chart1);
                chart1.render();

                var table = document.getElementById("redirect_table")
                table.innerHTML = makeRedirectTable()
            })
            .catch(function(error) {
                console.log(error)
            });


        },
        create(){
            axios.get('http://13.209.168.93:8081/count/create')
            .then(function(response){
                console.log(response);
                createArray = response.data.data;
                
                var chart2 = new CanvasJS.Chart("chartContainer_create", {
                    animationEnabled: true,
                    theme: "light2", // "light1", "light2", "dark1", "dark2"
                    title:{
                        text: "Top5 Creating Shorten Url"
                    },
                    axisY: {
                        title: "create count"
                    },
                    data: [{        
                        type: "column",  
                        showInLegend: true, 
                        legendMarkerColor: "grey",
                        dataPoints: makeArrayCreate()
                    }]
                });
                chart2.render();

                var table = document.getElementById("create_table")
                table.innerHTML = makeCreateTable()

            })
            .catch(function(error) {
                console.log(error)
            });

        }
    }
})


window.onload = function () {
    count.redirect()
    count.create()
}

function makeArrayCreate(){
    var result = []
    for( var i=0; i<createArray.length; i++){
        console.log(createArray[i]);
        var object = {y: createArray[i]["shortUrl"]["create_count"], label: createArray[i]["shortUrl"]["id_short"]}
        result.push(object)
    }
    console.log(result);
    return result
}

function makeArrayRedirect(){
    var result = []
    for( var i=0; i<redirectArray.length; i++){
        console.log(redirectArray[i]);
        var object = {y: redirectArray[i]["shortUrl"]["redirect_count"], label: +redirectArray[i]["shortUrl"]["idshort"]}
        result.push(object)
    }
    console.log(result);
    return result
}

function makeRedirectTable(){
    var templete = "";
    for(var i=0; i<redirectArray.length; i++){
        templete = templete +  
        '<tr>'+
        '<td>'+redirectArray[i]["shortUrl"]["idshort"]+'</td>'+
        '<td>'+redirectArray[i]["shorten"]+'</td>'+
        '<td>'+redirectArray[i]["shortUrl"]["link_url"]+'</td>'+
        '</tr>'
    }
    console.log(templete);
    return templete
}

function makeCreateTable(){
    var templete = "";
    for(var i=0; i<createArray.length; i++){
        templete = templete +  
        '<tr>'+
        '<td>'+createArray[i]["shortUrl"]["idshort"]+'</td>'+
        '<td>'+createArray[i]["shorten"]+'</td>'+
        '<td>'+createArray[i]["shortUrl"]["link_url"]+'</td>'+
        '</tr>'
    }
    console.log(templete);
    return templete
}