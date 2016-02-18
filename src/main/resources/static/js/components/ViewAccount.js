/**
 * Created by Thomas on 2/9/2016.
 */
var ViewAccount = React.createClass({
    mixins: [Reflux.connect(UserStore, 'userInformation')],
    //UserStore: UserStore.getUserInformation("thomas@test.com"),
    render: function () {
        UserStore.getUserInformation("thomas@test.com");
        if (this.state.userInformation) {
            return (
                <div>
                    <h1 className="text-center">
                        {this.state.userInformation.firstName} {this.state.userInformation.lastName}
                    </h1>
                    <h2 className="text-center">
                        {this.state.userInformation.email}
                    </h2>
                    <h2 className="text-center">
                        {this.state.userInformation.description}
                    </h2>
                    <h2 className="text-center">
                        {this.state.userInformation.currentCity}
                    </h2>
                </div>
            )
        } else {
            return (
                <span>Please wait while your information is being loaded...</span>
            )
        }
    }
});

ReactDOM.render(<ViewAccount />, document.getElementById('view_account'));