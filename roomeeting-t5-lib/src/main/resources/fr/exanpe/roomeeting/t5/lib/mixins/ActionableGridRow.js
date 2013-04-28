var ActionableGridRow = Class.create();
ActionableGridRow.prototype = {
	initialize : function(gridId, zoneId, cellSelector, url, multi, param) {
		this.gridId = gridId;
		this.zoneId = zoneId;
		this.cellSelector = cellSelector;
		this.url = url;
		this.multi = multi;
		this.param = param;
		
		if ($(gridId) != null) {
			var e = $(gridId).down('tbody');
			if(e!=null)
			{
				e.setStyle({cursor: 'pointer'});
				e = e.down('tr');
			}
			while (e != null) {
				Event.observe(e, 'click', this.onClickEvent.bindAsEventListener(this));
				e = e.next('tr');
			}
		}
	},
	onClickEvent : function(event) {
		// modifie l'apparence de la ligne sélectionnée
		var element = Event.findElement(event, 'tr');
		var others = element.siblings('tr');
		if (!this.multi) {
			for (i=0; i<others.length; i++) {
				if(others[i].className.indexOf("notDisplayed") < 0)
						{others[i].writeAttribute('class', 'blanc');}
			}
			element.writeAttribute('class', 'selected');
		} else {
			if (element.readAttribute('class') == 'selected') {
				element.writeAttribute('class', 'blanc');
			} else {
				element.writeAttribute('class', 'selected');
			}
				
		}

		// récupère la cellule contenant le paramètre de la requête
		element = element.down(this.cellSelector);

		var url;
		if (this.url.include('?')) {
			url = this.url + "&" + this.param + "=" + element.innerHTML;
		} else {
			url = this.url + "?" + this.param + "="+ element.innerHTML;
		}

		if (this.zoneId && this.zoneId != 'null') {
			// récupère le manager de zone pour la mise à jour
	        var zoneObject = Tapestry.findZoneManagerForZone(this.zoneId);
	        if (!zoneObject) return;
	        
	        // effectue la requête de mise à jour
	        zoneObject.updateFromURL(url);
		} else {
			// pas de zone à mettre à jour, redirection
			document.location = url;
		}
	}
};
