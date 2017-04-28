
function CircuitBreaker(id) {
	this.id = id;
}
CircuitBreaker.prototype = {
   "forceOpen": function() {
	    $.get("/circuit-breaker/" + this.id + "/force-open", function( data ) {});
   },
   
   "forceClose": function() {
	    $.get("/circuit-breaker/" + this.id + "/force-close", function( data ) {});	   
   },
   
   "reset": function() {
	    $.get("/circuit-breaker/" + this.id + "/reset", function( data ) {});
   },   
   "enable": function() {
	    $.get("/circuit-breaker/" + this.id + "/enable", function( data ) {});
   },   
   "disable": function() {
	    $.get("/circuit-breaker/" + this.id + "/disable", function( data ) {});
   }   
   
};

function Bank(id) {
	this.id = id;
	this.elem = $('#BANK' + id);
	this.init(id);
}

Bank.prototype = {	
	"init": function(id) {
		var elem = $('#BANK' + id);
		var that = this;
		this.elem.find('#BANK' + id + '-error').change( function() {
			var errorRate = this.value;
			that.setErrorRate(errorRate);
			that.elem.find('#BANK' + id + '-error-value').html(errorRate);
		});
		this.elem.find('#BANK' + id + '-speed').change( function() {
			var delay = this.value;
			that.setDelay(delay*1000);
			that.elem.find('#BANK' + id + '-speed-value').html(delay);
		});
	},
    "setErrorRate": function(value) {
	    var url = "/control/" + this.id + "/error/" + value;
	    $.get(url, function( data ) {	});
    },
    "setDelay": function(value) {
	    var url = "/control/" + this.id + "/delay/" + value;
	    $.get(url, function( data ) {	});
    },
    "enable": function(value) {
	    var url = "/control/" + this.id + "/enable";
	    $.get(url, function( data ) {	});
    },
    "disable": function(value) {
	    var url = "/control/" + this.id + "/disable";
	    $.get(url, function( data ) {	});
    }
    
}
var banks = [
	new Bank(1), new Bank(2), new Bank(3), new Bank(4), new Bank(5)
];

function getBank(id) {
	for (var i = 0; i < banks.length; i++) {
		 if (banks[i].id == id) {
			 return banks[i];
		 }
	}	
}

var circuitBreakers = [
	new CircuitBreaker("BANK1"), new CircuitBreaker("BANK2"), 
	new CircuitBreaker("BANK3"), new CircuitBreaker("BANK4"), 
	new CircuitBreaker("BANK5")
];

function getCircuitBreaker(id) {
	for (var i = 0; i < circuitBreakers.length; i++) {
		 if (circuitBreakers[i].id == id) {
			 return circuitBreakers[i];
		 }
	}	
}
$('.btn-toggle').click(function() {
    $(this).find('.btn').toggleClass('active');

    if ($(this).find('.btn-primary').length>0) {
        $(this).find('.btn').toggleClass('btn-primary');
    }
    if ($(this).find('.btn-danger-toggle').length>0) {
        $(this).find('.btn-danger-toggle').toggleClass('btn-danger');
    }
    if ($(this).find('.btn-success-toggle').length>0) {
        $(this).find('.btn-success-toggle').toggleClass('btn-success');
    }
    if ($(this).find('.btn-info').length>0) {
        $(this).find('.btn').toggleClass('btn-info');
    }

    $(this).find('.btn').toggleClass('btn-default');
    var id = this.dataset.id;
    var txt = $(this).find('.btn.active').text();
    if (txt == 'ON') {
    	getBank(id).enable();
    } else {
    	getBank(id).disable();
    }
});

$('input.bank-cb').change(function() {
	var cb = getCircuitBreaker(this.dataset.bankId);
	if (cb) {
		if (this.value == 'FO') {
			cb.forceOpen();
		} else if (this.value == 'FC') {
			cb.forceClose();
		} else {
			cb.reset();
		}
	}
});

$('button.cb-disable').click(function() {
	var cb = getCircuitBreaker(this.dataset.bankId);
	var state = this.dataset.state;
	
	if (cb) {
		if (state == "1") {
			this.dataset.state = "0";
			cb.disable();
			$(this).text('Disabled');
		} else {
			this.dataset.state = "1";
			cb.enable();
			$(this).text("Enabled")
		}

	}
})

