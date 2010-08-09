<ul class="navigation">
	<g:each in="${navigation.viewcomponent}" var="viewcomponent">
		<li class="navigationItem firstItem hasChilden">
			<a href="${urlLinkName + "/" + viewcomponent.urlLinkName.text()}.html">${viewcomponent.linkName.text()} </a>
			<g:if test="${viewcomponent.@hasChild}">
				<g:render template="components/navigation" model="${[navigation: viewcomponent, urlLinkName: urlLinkName + '/' + viewcomponent.urlLinkName.text()]}" plugin="tizzitWeb"/>
			</g:if>
		</li>
	</g:each>
</ul>
