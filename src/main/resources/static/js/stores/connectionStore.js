var ConnectionStore = Reflux.createStore({
    listenables: [ConnectionActions],
    onGetConnections : function() {
        $.ajax({
            url: "/api/connections/get",
            dataType: 'json',
            success : this.triggerConnections
        })
    },
    triggerConnections : function(data) {
        console.log(data);
        this.trigger(data);
    },
    onGetConnectionGroups : function() {
        $.ajax({
            url: "/api/connectionGroups",
            dataType: 'json',
            success : this.triggerConnections
        });
    },
    onGetConnectionStatus : function() {
        var id = window.location.href.substr(window.location.href.lastIndexOf('/') + 1);
        $.ajax({
            url: '/api/connection/status/' + id,
            dataType: 'text',
            success : this.triggerStatus,
            error: function () {
                console.log("ya dun goofed");
            }
        });
    },
    onGetConnectionGroup : function() {
        var id = window.location.href.substr(window.location.href.lastIndexOf('/') + 1);

        $.ajax({
            url: '/api/connectionGroup/' + id,
            dataType: 'json',
            success : this.triggerStatus
        })
    },
    onAddConnectionGroup : function(groupName, groupId) {
        console.log(groupName);
        console.log(groupId);
        var url = '/api/connectionGroup/create';
        if (groupId != undefined) {
            url = '/api/connectionGroup/' + groupId + '/edit'
        }
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: url,
            data: groupName,
            type: 'POST',
            success : function() {
                console.log("success dude");
                window.location.reload(true);
            }
        })
    },
    onDeleteConnectionGroup : function(groupId) {
        console.log(groupId);
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/api/connectionGroup/delete',
            data: JSON.stringify(groupId),
            type: 'POST',
            success : function() {
                console.log("success dude");
                window.location.reload(true);
            }
        })
    },
    onRejectConnection : function() {
        this.handleConnectionAjax('/api/connection/reject/', 'POST');
    },
    onRemoveConnection : function() {
       this.handleConnectionAjax('/api/connection/remove/', 'POST');
    },
    onRequestConnection : function() {
        this.handleConnectionAjax('/api/connection/request/', 'POST');
    },
    onAddConnection : function() {
        this.handleConnectionAjax('/api/connection/add/', 'POST');
    },
    handleConnectionAjax : function(url, type) {
        var id = window.location.href.substr(window.location.href.lastIndexOf('/') + 1);
        $.ajax({
            url: url + id,
            dataType: 'text',
            type: type,
            success : this.triggerStatus,
            error: function () {
                console.log("ya dun goofed");
            }
        });
    },
    triggerStatus : function(data) {
        this.trigger(data);
    },
    onAddConnectionToGroup : function(userId) {
    //    TODO jeffreyh 3/18/16
    },
    onRemoveConnectionFromGroup : function(userId) {
    //    TODO jeffreyh 3/18/16
    }

});